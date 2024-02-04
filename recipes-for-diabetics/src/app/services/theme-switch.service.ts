import { Inject, Injectable } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ThemeSwitchService {

  constructor(@Inject(DOCUMENT) private document: Document) { 

    this.theme = this.document.documentElement.classList.contains(ThemeSwitchService.DARK_THEME_CLASS) ? ThemeSwitchService.DARK_THEME_DARK : ThemeSwitchService.DARK_THEME_LIGHT;
    this.themeChangeEvent = new BehaviorSubject<string>(this.theme);
  }

  public theme: string;
  public static readonly DARK_THEME_CLASS = 'dark-theme';
  public static readonly DARK_THEME_LIGHT = 'light';
  public static readonly DARK_THEME_DARK = 'dark';
  private themeChangeEvent:BehaviorSubject<string>;

  public getThemeChangeEvent():BehaviorSubject<string> {
    return this.themeChangeEvent;
  }
  public setTheme(theme:string) {
    this.theme = theme;
    this.themeChangeEvent.next(this.theme)
  }
  public selectDarkTheme(): void {
    
    this.document.documentElement.classList.add(ThemeSwitchService.DARK_THEME_CLASS);
    this.theme = ThemeSwitchService.DARK_THEME_DARK;
    this.themeChangeEvent.next(this.theme);
  }

  public selectLightTheme(): void {
    this.document.documentElement.classList.remove(ThemeSwitchService.DARK_THEME_CLASS);
    this.theme = ThemeSwitchService.DARK_THEME_LIGHT;
    this.themeChangeEvent.next(this.theme);
  }
}
