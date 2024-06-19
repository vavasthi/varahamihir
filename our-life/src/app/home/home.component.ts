import { Component } from '@angular/core';
import { MainMenuBarComponent } from "../main-menu-bar/main-menu-bar.component";
import { CustomSidenavMenuItems } from '../pojo/custom-sidenav-menu-items';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormsModule, FormControl, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { MathjaxDirective } from '../directives/mathjax.directive';
import { EquationService } from '../services/equation.service';
import { Equation } from '../pojo/equation';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  imports: [MainMenuBarComponent,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    ReactiveFormsModule,
    MathjaxDirective,
    FormsModule]
})
export class HomeComponent {

  equations:Equation[] = []
  constructor(private equationService:EquationService){
    equationService.getAllEquations().subscribe(pe => {
      console.log(pe)
      this.equations = pe.content
      console.log(this.equations)
    })
  }

  testForm: FormGroup = new FormGroup({
    input: new FormControl('Username Initial Value')
  })
  onTest() {
  }

}
