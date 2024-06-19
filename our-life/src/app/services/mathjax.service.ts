import { Injectable } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';

interface MathJaxConfig {
  source: string;
  integrity: string;
  id: string;
}

declare global {
  interface Window {
    MathJax: {
      typesetPromise: () => void;
      startup: {
        promise: Promise<any>;
      };
    };
  }
}

@Injectable({
  providedIn: 'root'
})
export class MathjaxService {

  private signal: Subject<void>;
  private mathJax: MathJaxConfig = {
    source: "https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js",
    integrity: "sha256-Cm3tWrvOEzMWWN0jnzQ4Kr0GSSx0txth6MqoES7FX6U=",
    id: "MathJaxScript"
  };
  private mathJaxFallback: MathJaxConfig = {
    source: "assets/mathjax/tex-chtml.js",
    integrity: "sha256-Cm3tWrvOEzMWWN0jnzQ4Kr0GSSx0txth6MqoES7FX6U=",
    id: "MathJaxBackupScript"
  };

  constructor() {
    this.signal = new ReplaySubject<void>();
    void this.registerMathJaxAsync(this.mathJax)
      .then(() => this.signal.next())
      .catch(error => {
         void this.registerMathJaxAsync(this.mathJaxFallback)
          .then(() => this.signal.next())
          .catch((error) => console.log(error));
      });
  }

  private async registerMathJaxAsync(config: MathJaxConfig): Promise<any> {
    return new Promise<void>((resolve, reject) => {
      const script: HTMLScriptElement = document.createElement("script");
      script.id = config.id;
      script.type = "text/javascript";
      script.src = config.source;
      script.integrity = config.integrity;
      script.crossOrigin = "anonymous";
      script.async = true;
      script.onload = () => resolve();
      script.onerror = error => reject(error);
      document.head.appendChild(script);
    });
  }

  ready(): Observable<void> {
    return this.signal;
  }

  render(element: HTMLElement, math?: string) {
    // Take initial typesetting which MathJax performs into account
    window.MathJax.startup.promise.then(() => {
      console.log("Update SVG", math, "Hello")
      if (math) {

        element.innerHTML = math;
        window.MathJax.typesetPromise();
        }
    });
  }
}
