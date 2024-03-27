import { Inject, Injectable, Renderer2, RendererFactory2 } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ThemeSwitchService {

  private renderer: Renderer2;

  constructor(@Inject(DOCUMENT) private document: Document, rendererFactory: RendererFactory2) { 

    this.theme = this.document.documentElement.classList.contains(ThemeSwitchService.DARK_THEME_CLASS) ? ThemeSwitchService.DARK_THEME_DARK : ThemeSwitchService.DARK_THEME_LIGHT;
    this.themeChangeEvent = new BehaviorSubject<string>(this.theme);
    this.renderer = rendererFactory.createRenderer(null, null);
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
