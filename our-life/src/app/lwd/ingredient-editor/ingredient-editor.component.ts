import { Component, Input, Signal, WritableSignal, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { IngredientService } from '../services/ingredient.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IngredientWithNutrition } from '../pojo/ingredient-with-nutrition';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { DragAndDropComponent } from "../../drag-and-drop/drag-and-drop.component";
import { MatFormFieldModule, MatFormFieldControl } from '@angular/material/form-field';
import { AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatChipEditedEvent, MatChipInputEvent, MatChipsModule } from '@angular/material/chips';
import { FileUploadServiceService } from '../../services/file-upload-service.service';
import { Content } from '../../pojo/content';
import { UnitService } from '../services/unit.service';
import { Unit } from '../pojo/unit';
import { MatSelectModule } from '@angular/material/select';
import { AuthService } from '../../services/auth.service';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Ingredient } from '../pojo/ingredient';
import { MatIconModule } from '@angular/material/icon';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { QuantityTypePipe } from "../pipes/units/quantity-type.pipe";
import { MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { Location } from '@angular/common';

@Component({
  selector: 'app-ingredient-editor',
  standalone: true,
  templateUrl: './ingredient-editor.component.html',
  styleUrl: './ingredient-editor.component.scss',
  imports: [RouterModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatChipsModule,
    MatIconModule,
    MatCheckboxModule,
    DragAndDropComponent,
    QuantityTypePipe,
  MatDialogModule]
})
export class IngredientEditorComponent {

  ingredientEditorForm: FormGroup = new FormGroup({
    name: new FormControl(''),
    description: new FormControl(''),
    url: new FormControl(''),
    brand: new FormControl(''),
    quantity: new FormControl<number|null>(null, Validators.required),
    unitType: new FormControl<string|null>(null, Validators.required),
    unit: new FormControl<Unit|null>(null, Validators.required),
    weightQuantity: new FormControl<number|null>(null),
    weightUnit: new FormControl<Unit|null>(null),
    volumeQuantity: new FormControl<number|null>(null),
    volumeUnit: new FormControl<Unit|null>(null),
    calories: new FormControl<number|null>(null, Validators.required),
    totalFat: new FormControl(''),
    transFat: new FormControl(' '),
    saturatedFat: new FormControl(' '),
    polyUnsaturatedFat: new FormControl(''),
    monoUnsaturatedFat: new FormControl(''),
    cholestrol: new FormControl(''),
    sodium: new FormControl(''),
    totalCarbohydrates: new FormControl(''),
    dietaryFiber: new FormControl(''),
    sugars: new FormControl(''),
    protein: new FormControl(''),
    calcium: new FormControl(''),
    potassium: new FormControl(''),
    iron: new FormControl(''),
    magnesium: new FormControl(''),
    cobalamin: new FormControl(''),
    vitaminA: new FormControl(''),
    vitaminC: new FormControl(''),
    vitaminB6: new FormControl(''),
    vitaminD: new FormControl(''),
  })
  url: WritableSignal<string | undefined> = signal(undefined)

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] = [];


  @Input() id = '';
  unitList: Signal<Unit[] | undefined> = this.unitService.getUnits();
  unitTypeList = new Set<string>(['Both']);
  ingredientWithNutrition: IngredientWithNutrition | undefined;
  images: Content[] = [];
  permittedUnitTypes:WritableSignal<string|undefined> = signal(undefined);

  constructor(private ingredientService: IngredientService,
    private snackBar: MatSnackBar,
    private fileUploadService: FileUploadServiceService,
    private authService: AuthService,
    private announcer: LiveAnnouncer,
    private location:Location,
    private unitService: UnitService) {

  }
  ngOnInit(): void {

    this.unitService.loadAllUnits().subscribe(ul => {
      ul.forEach(unit => {
        this.unitTypeList.add(unit.quantityType)
      })
    })
    if (this.id) {

      this.loadIngredient(false)
    }
    this.ingredientEditorForm.controls['unitType'].valueChanges.subscribe(ut => {
      console.log("Changed unit type", ut)
      this.permittedUnitTypes.set(ut);
      if (ut === "Both") {
        this.ingredientEditorForm.controls['weightQuantity'].setValidators(Validators.required);
        this.ingredientEditorForm.controls['weightUnit'].setValidators(Validators.required);
        this.ingredientEditorForm.controls['volumeQuantity'].setValidators(Validators.required);
        this.ingredientEditorForm.controls['volumeUnit'].setValidators(Validators.required);
      }
      else {

        this.ingredientEditorForm.controls['weightQuantity'].removeValidators(Validators.required);
        this.ingredientEditorForm.controls['weightUnit'].removeValidators(Validators.required);
        this.ingredientEditorForm.controls['volumeQuantity'].removeValidators(Validators.required);
        this.ingredientEditorForm.controls['volumeUnit'].removeValidators(Validators.required);
      }
    })
  }
  loadIngredient(retrying: boolean) {

    this.ingredientService.getIngredient(this.id).subscribe({
      next: (response) => {
        this.ingredientWithNutrition = response as IngredientWithNutrition;
        if (this.ingredientWithNutrition.ingredient.tags) {

          this.tags = this.ingredientWithNutrition.ingredient.tags
        }
        this.snackBar.open("Ingredients Loaded", "Ok", { duration: 5000 })
      },
      error: (err) => {
        if (err.status == 403 && !retrying) {
          this.authService.refresh()
          this.loadIngredient(true);
        }
        this.snackBar.open("Ingredients Load Failed", "Ok", { duration: 5000 })
      }
    })
  }
  onFileSelected(event: any) {

    var fileList = event as FileList
        this.fileUploadService.uploadFiles(fileList).subscribe(response => {
          console.log("Multiple file uploads", response)
          this.images = response as Content[];
        })
  }
  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.tags.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();
  }

  remove(tag: string): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);

      this.announcer.announce(`Removed ${tag}`);
    }
  }

  edit(tag: string, event: MatChipEditedEvent) {
    const value = event.value.trim();

    // Remove fruit if it no longer has a name
    if (!value) {
      this.remove(value);
      return;
    }

    // Edit existing fruit
    const index = this.tags.indexOf(value);
    if (index >= 0) {
      this.tags[index] = value;
    }
  }
  unitListOfQuantityType(quantityType:string) {
    return this.unitList()?.filter(unit => unit.quantityType === quantityType)
  }
  onCancel(event: any): void {
    this.location.back();
  }
}
