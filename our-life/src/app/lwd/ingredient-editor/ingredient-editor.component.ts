import { Component, Input, Signal, WritableSignal, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { IngredientService } from '../services/ingredient.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IngredientWithNutrition } from '../pojo/ingredient-with-nutrition';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { DragAndDropComponent } from "../../drag-and-drop/drag-and-drop.component";
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
import {Nutrition} from "../pojo/nutrition";
import {Quantity} from "../pojo/quantity";
import {UnitConversion} from "../pojo/unit-conversion";
import {validateTotalFat} from "../validators/validate-total-fat";
import {validateTotalCarbohydrates} from "../validators/validate-total-carbohydrates";

@Component({
    selector: 'app-ingredient-editor',
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
    totalFat: new FormControl<number|null>(null, Validators.required),
    transFat: new FormControl<number|null>(null),
    saturatedFat: new FormControl<number|null>(null),
    polyUnsaturatedFat: new FormControl<number|null>(null),
    monoUnsaturatedFat: new FormControl<number|null>(null),
    cholesterol: new FormControl<number|null>(null),
    sodium: new FormControl<number|null>(null),
    totalCarbohydrates: new FormControl<number|null>(null),
    dietaryFiber: new FormControl<number|null>(null),
    sugars: new FormControl<number|null>(null),
    protein: new FormControl<number|null>(null),
    calcium: new FormControl<number|null>(null),
    potassium: new FormControl<number|null>(null),
    iron: new FormControl<number|null>(null),
    magnesium: new FormControl<number|null>(null),
    cobalamin: new FormControl<number|null>(null),
    vitaminA: new FormControl<number|null>(null),
    vitaminC: new FormControl<number|null>(null),
    vitaminB6: new FormControl<number|null>(null),
    vitaminD: new FormControl<number|null>(null)
  }, {
    validators : [ validateTotalFat(), validateTotalCarbohydrates()]
  })
  url: WritableSignal<string | undefined> = signal(undefined)

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] = [];


  @Input() id = '';
  unitList: Signal<Unit[] | undefined> = this.unitService.getUnits();
  unitTypeList = new Set<string>(['Both']);
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
/*
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
    })*/
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
  onSave(event: any): void {
    const gramUnit = "Gram"
    const mgUnit = "Milligram"
    const mcgUnit = "Microgram"
    const ingredient = {} as Ingredient;
    ingredient.name = this.ingredientEditorForm.controls['name'].value;
    ingredient.id = this.id;
    ingredient.url = this.images[0].url;
    ingredient.brand = this.ingredientEditorForm.controls['brand'].value;
    ingredient.tags = this.tags;
    ingredient.description = this.ingredientEditorForm.controls['description'].value;
    if (this.permittedUnitTypes() && this.permittedUnitTypes() == 'Both' ) {

      ingredient.unitConversion = { weightQuantity: this.ingredientEditorForm.controls['weightQuantity'].value,
        weightUnit: this.ingredientEditorForm.controls['weightUnit'].value.value,
        volumeQuantity: this.ingredientEditorForm.controls['volumeQuantity'].value,
        volumeUnit: this.ingredientEditorForm.controls['volumeUnit'].value.value
      } as UnitConversion;
    }
    ingredient.unitType = this.ingredientEditorForm.controls['unitType'].value;

    const nutrition = {} as Nutrition;
    nutrition.calories = this.ingredientEditorForm.controls['calories'].value;
    nutrition.quantity = {value : this.ingredientEditorForm.controls['quantity'].value, unit:this.ingredientEditorForm.controls['unit'].value.value} as Quantity
    nutrition.totalFat = { value  :  this.ingredientEditorForm.controls['totalFat'].value, unit : gramUnit} as Quantity;
    nutrition.saturatedFat = this.ingredientEditorForm.controls['saturatedFat'].value == null ? null : { value : this.ingredientEditorForm.controls['saturatedFat'].value, unit : gramUnit} as Quantity;
    nutrition.transFat = this.ingredientEditorForm.controls['transFat'].value == null ? null : { value : this.ingredientEditorForm.controls['transFat'].value, unit : gramUnit} as Quantity;
    nutrition.polyUnsaturatedFat = this.ingredientEditorForm.controls['polyUnsaturatedFat'].value == null ? null : { value : this.ingredientEditorForm.controls['polyUnsaturatedFat'].value, unit : gramUnit} as Quantity;
    nutrition.monoUnsaturatedFat = this.ingredientEditorForm.controls['monoUnsaturatedFat'].value == null ? null : { value : this.ingredientEditorForm.controls['monoUnsaturatedFat'].value, unit : gramUnit} as Quantity;
    nutrition.cholesterol = this.ingredientEditorForm.controls['cholesterol'].value == null ? null : { value : this.ingredientEditorForm.controls['cholesterol'].value, unit : mgUnit} as Quantity;
    nutrition.totalCarbohydrates = { value  :  this.ingredientEditorForm.controls['totalCarbohydrates'].value, unit : gramUnit} as Quantity;
    nutrition.dietaryFiber = this.ingredientEditorForm.controls['dietaryFiber'].value == null ? null : { value  :  this.ingredientEditorForm.controls['dietaryFiber'].value, unit : gramUnit} as Quantity;
    nutrition.sugars = this.ingredientEditorForm.controls['sugars'].value == null ? null : { value  :  this.ingredientEditorForm.controls['sugars'].value, unit : gramUnit} as Quantity;
    nutrition.protein = this.ingredientEditorForm.controls['protein'].value == null ? null : { value  :  this.ingredientEditorForm.controls['protein'].value, unit : gramUnit} as Quantity;
    nutrition.sodium = this.ingredientEditorForm.controls['sodium'].value == null ? null : { value  :  this.ingredientEditorForm.controls['sodium'].value, unit : mgUnit} as Quantity;
    nutrition.calcium = this.ingredientEditorForm.controls['calcium'].value == null ? null : { value  :  this.ingredientEditorForm.controls['calcium'].value, unit : mgUnit} as Quantity;
    nutrition.potassium = this.ingredientEditorForm.controls['potassium'].value == null ? null : { value  :  this.ingredientEditorForm.controls['potassium'].value, unit : mgUnit} as Quantity;
    nutrition.iron = this.ingredientEditorForm.controls['iron'].value == null ? null : { value  :  this.ingredientEditorForm.controls['iron'].value, unit : mgUnit} as Quantity;
    nutrition.magnesium = this.ingredientEditorForm.controls['magnesium'].value == null ? null : { value  :  this.ingredientEditorForm.controls['magnesium'].value, unit : mgUnit} as Quantity;
    nutrition.cobalamin = this.ingredientEditorForm.controls['cobalamin'].value == null ? null : { value  :  this.ingredientEditorForm.controls['cobalamin'].value, unit : mcgUnit} as Quantity;
    nutrition.vitaminA = this.ingredientEditorForm.controls['vitaminA'].value == null ? null : { value  :  this.ingredientEditorForm.controls['vitaminA'].value, unit : mgUnit} as Quantity;
    nutrition.vitaminC = this.ingredientEditorForm.controls['vitaminC'].value == null ? null : { value  :  this.ingredientEditorForm.controls['vitaminC'].value, unit : mgUnit} as Quantity;
    nutrition.vitaminB6 = this.ingredientEditorForm.controls['vitaminB6'].value == null ? null : { value  :  this.ingredientEditorForm.controls['vitaminB6'].value, unit : mgUnit} as Quantity;
    nutrition.vitaminD = this.ingredientEditorForm.controls['vitaminD'].value == null ? null : { value  :  this.ingredientEditorForm.controls['vitaminD'].value, unit : mgUnit} as Quantity;
    const ingredientWithNutrition = {ingredient : ingredient, nutrition : nutrition} as IngredientWithNutrition;
    this.ingredientService.createIngredient(ingredientWithNutrition).subscribe(iwn => {
      console.log(iwn)
      this.location.back();
    })
  }
}
