import { Component, Input, Signal, WritableSignal, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { IngredientService } from '../services/ingredient.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IngredientWithNutrition } from '../pojo/ingredient-with-nutrition';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { DragAndDropComponent } from "../../drag-and-drop/drag-and-drop.component";
import { MatFormFieldModule, MatFormFieldControl} from '@angular/material/form-field';
import {AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, ValidationErrors, ValidatorFn} from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import {MatChipEditedEvent, MatChipInputEvent, MatChipsModule} from '@angular/material/chips';
import { FileUploadServiceService } from '../../services/file-upload-service.service';
import { Content } from '../../pojo/content';
import { UnitService } from '../services/unit.service';
import { Unit } from '../pojo/unit';
import {MatSelectModule} from '@angular/material/select';
import { AuthService } from '../../services/auth.service';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import { Ingredient } from '../pojo/ingredient';
import { MatIconModule } from '@angular/material/icon';
import {LiveAnnouncer} from '@angular/cdk/a11y';

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
        DragAndDropComponent]
})
export class IngredientEditorComponent {

  ingredientEditorForm:FormGroup = new FormGroup({
    name:new FormControl(''),
    description: new FormControl(''),
    url: new FormControl(''),
    brand: new FormControl(''),
    quantity: new FormControl(''),
    unit: new FormControl(''),
    calories: new FormControl(''),
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
  url:WritableSignal<string|undefined> = signal(undefined)

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] = [];


  @Input() id = '';
  unitList =this.unitService.getUnits()
  ingredientWithNutrition: IngredientWithNutrition | undefined;

  constructor(private ingredientService: IngredientService,
    private snackBar: MatSnackBar,
    private fileUploadService: FileUploadServiceService,
    private authService:AuthService,
    private announcer:LiveAnnouncer,
    private unitService: UnitService) {

  }
  ngOnInit(): void {

    if (this.id) {

      this.loadIngredient(false)
    }
  }
  loadIngredient(retrying:boolean) {

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
    this.fileUploadService.upload(fileList[0]).subscribe ( response => {
      console.log(response)
      var content = response as Content;
      this.url.set(content.url)
      this.ingredientEditorForm.patchValue({url : content.url})
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
}
