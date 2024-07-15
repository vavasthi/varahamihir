import { Inject, Injectable, Renderer2, RendererFactory2 } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ThemeSwitchService {

  private renderer: Renderer2;

  constructor(@Inject(DOCUMENT) private document: Document, rendererFactory: RendererFactory2) {

    this.document.documentElement.classList.add(ThemeSwitchService.LIGHT_THEME_CLASS);
    this.document.documentElement.classList.remove(ThemeSwitchService.DARK_THEME_CLASS);
    this.CURRENT_THEME_CLASS = ThemeSwitchService.LIGHT_THEME_CLASS;

    this.themeChangeEvent = new BehaviorSubject<string>(this.CURRENT_THEME_CLASS);
    this.renderer = rendererFactory.createRenderer(null, null);
  }

  public static readonly DARK_THEME_CLASS = 'dark-theme';
  public static readonly LIGHT_THEME_CLASS = 'light-theme';
  public CURRENT_THEME_CLASS = 'light-theme';

  private themeChangeEvent: BehaviorSubject<string>;

  public getThemeChangeEvent(): BehaviorSubject<string> {
    return this.themeChangeEvent;
  }
  public toggleTheme(): void {

    if (this.CURRENT_THEME_CLASS == ThemeSwitchService.DARK_THEME_CLASS) {

      this.document.documentElement.classList.add(ThemeSwitchService.LIGHT_THEME_CLASS);
      this.document.documentElement.classList.remove(ThemeSwitchService.DARK_THEME_CLASS);
      this.CURRENT_THEME_CLASS = ThemeSwitchService.LIGHT_THEME_CLASS;
      console.log("Setting light theme")
    }
    else {

      this.document.documentElement.classList.add(ThemeSwitchService.DARK_THEME_CLASS);
      this.document.documentElement.classList.remove(ThemeSwitchService.LIGHT_THEME_CLASS);
      this.CURRENT_THEME_CLASS = ThemeSwitchService.DARK_THEME_CLASS;
      console.log("Setting dark theme")
    }
    this.themeChangeEvent.next(this.CURRENT_THEME_CLASS);
  }
}
