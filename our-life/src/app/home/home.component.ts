import {Component} from '@angular/core';
import {MainMenuBarComponent} from "../main-menu-bar/main-menu-bar.component";
import {CustomSidenavMenuItems} from '../pojo/custom-sidenav-menu-items';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormsModule, FormControl, Validators, ReactiveFormsModule, FormGroup} from '@angular/forms';
import {MathjaxDirective} from '../directives/mathjax.directive';
import {EquationService} from '../services/equation.service';
import {Equation} from '../pojo/equation';
import {EmbedComponent} from "../embed/embed.component";
import {MediaType} from "../pojo/media-type";
import {EmbedService} from "../services/embed.service";
import {Embed} from "../pojo/embed";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  imports: [MainMenuBarComponent,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    ReactiveFormsModule,
    MathjaxDirective,
    FormsModule,
    EmbedComponent]
})
export class HomeComponent {

  //  embed:string = "Hello World"
  equations: Equation[] = []
  embeds: Embed[] = []

  constructor(private equationService: EquationService,
              private embedService:EmbedService) {
    embedService.getAllEmbeds().subscribe(pe => {
      this.embeds = pe.content
    })
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
