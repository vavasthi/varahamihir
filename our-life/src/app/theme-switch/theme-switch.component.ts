import { Component, Inject, Input } from '@angular/core';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { DOCUMENT } from '@angular/common';
import { BehaviorSubject } from 'rxjs';
import { ThemeSwitchService } from '../services/theme-switch.service';

@Component({
  selector: 'app-theme-switch',
  standalone: true,
  imports: [MatButtonToggleModule],
  templateUrl: './theme-switch.component.html',
  styleUrl: './theme-switch.component.scss'
})
export class ThemeSwitchComponent {

  @Input() theme:string;
  constructor(private themeSwitchService:ThemeSwitchService,
    @Inject(DOCUMENT) private document: Document) {

      this.theme = this.document.documentElement.classList.contains(ThemeSwitchService.DARK_THEME_CLASS) ? ThemeSwitchService.DARK_THEME_DARK : ThemeSwitchService.DARK_THEME_LIGHT;
      themeSwitchService.getThemeChangeEvent().subscribe( theme => {
        this.theme = theme;
      })
  }
  public selectDarkTheme(): void {

    this.themeSwitchService.selectDarkTheme();
  }

  public selectLightTheme(): void {
    this.themeSwitchService.selectLightTheme();
  }
  public setTheme(theme:string) {
    this.theme = theme;
  }
}
