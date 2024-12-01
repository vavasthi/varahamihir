import {Component} from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MathjaxDirective} from '../directives/mathjax.directive';
import {EquationService} from '../services/equation.service';
import {Equation} from '../pojo/equation';
import {EmbedComponent} from "../embed/embed.component";
import {EmbedService} from "../services/embed.service";
import {Embed} from "../pojo/embed";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  imports: [MatCardModule,
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
