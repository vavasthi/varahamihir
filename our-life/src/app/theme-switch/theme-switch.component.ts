import { Component, Inject, Input } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { DOCUMENT } from '@angular/common';
import { BehaviorSubject } from 'rxjs';
import { ThemeSwitchService } from '../services/theme-switch.service';

@Component({
  selector: 'app-theme-switch',
  standalone: true,
  imports: [MatSlideToggleModule
],
  templateUrl: './theme-switch.component.html',
  styleUrl: './theme-switch.component.scss'
})
export class ThemeSwitchComponent {
isChecked: any = false;
themeType:string = "Light";

  constructor(private themeSwitchService:ThemeSwitchService,
    @Inject(DOCUMENT) private document: Document) {
      themeSwitchService.getThemeChangeEvent().subscribe(theme => {
        this.isChecked = (theme == ThemeSwitchService.DARK_THEME_CLASS ? true : false);
        this.themeType = (theme == ThemeSwitchService.DARK_THEME_CLASS ? "Dark" : "Light");
      })
  }
  public toggleTheme(): void {

    this.themeSwitchService.toggleTheme();
  }
}
