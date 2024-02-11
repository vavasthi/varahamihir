import { Component, Input, Signal, WritableSignal, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { IngredientService } from '../services/ingredient.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IngredientWithNutrition } from '../pojo/ingredient-with-nutrition';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { DragAndDropComponent } from "../drag-and-drop/drag-and-drop.component";
import { MatFormFieldModule, MatFormFieldControl} from '@angular/material/form-field';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { FileUploadServiceService } from '../services/file-upload-service.service';
import { Content } from '../pojo/content';

@Component({
    selector: 'app-ingredient-editor',
    standalone: true,
    templateUrl: './ingredient-editor.component.html',
    styleUrl: './ingredient-editor.component.scss',
    imports: [RouterModule,
        MatButtonModule,
        MatCardModule, 
        MatInputModule,
        FormsModule,
        ReactiveFormsModule,
        DragAndDropComponent]
})
export class IngredientEditorComponent {

  ingredientEditorForm:FormGroup = new FormGroup({
    name:new FormControl(''),
    description: new FormControl(''),
    url: new FormControl(''),
    brand: new FormControl('')
  })
  url:WritableSignal<string|undefined> = signal(undefined)

  @Input() id = '';
  ingredientWithNutrition: IngredientWithNutrition | undefined;
  constructor(private ingredientService: IngredientService,
    private snackBar: MatSnackBar,
    private fileUploadService: FileUploadServiceService) {

  }
  ngOnInit(): void {

    if (this.id) {

      this.ingredientService.getIngredient(this.id).subscribe({
        next: (response) => {
          this.snackBar.open("Ingredients Loaded", "Ok", { duration: 5000 })
        },
        error: (err) => {
          this.snackBar.open("Ingredients Load Failed", "Ok", { duration: 5000 })
        }
      })
    }
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
}
