import {Component, inject} from "@angular/core";
import {MatDialogActions, MatDialogContent, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatLabel} from "@angular/material/form-field";
import {MatInputModule} from '@angular/material/input';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {EmbedService} from "../services/embed.service";

@Component({
  selector: 'home-config-add-embed-dialog',
  templateUrl: 'home-config-add-embed-dialog.html',
  styleUrl: './home-config-add-embed-dialog.scss',
  imports: [
    MatDialogModule,
    FormsModule,
    MatFormFieldModule,
    MatLabel,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule
  ],
})
export class HomeConfigAddEmbedDialog {
  constructor(private embedService:EmbedService) {
  }
  readonly dialogRef = inject(MatDialogRef<HomeConfigAddEmbedDialog>);
  embedForm: FormGroup = new FormGroup({
    code: new FormControl('')
  })
  addEmbed() : void {
    console.log("Saving Code..", this.embedForm.controls['code'].value);
    this.embedService.createEmbed(this.embedForm.controls['code'].value).subscribe(embed => {
      console.log(embed);
    });
    this.dialogRef.close();
  }
}
