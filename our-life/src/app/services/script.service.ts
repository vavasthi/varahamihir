import {ElementRef, Inject, Injectable, Renderer2} from '@angular/core';
import {DOCUMENT} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class ScriptService {
  constructor(
    @Inject(DOCUMENT) private document: Document) {

  }

  /**
   * Append the JS tag to the Document Body.
   * @param renderer The Angular Renderer
   * @param src The path to the script
   * @returns the script element
   */
  public loadJsScript(renderer: Renderer2, element: ElementRef, src: string): HTMLScriptElement {
    const script = renderer.createElement('script');
    script.type = 'text/javascript';
    script.src = src;
    renderer.appendChild(element.nativeElement, script);
    return script;
  }
}
