import {Component, ElementRef, Input, Renderer2} from '@angular/core';
import {DomSanitizer, SafeHtml} from '@angular/platform-browser';
import {BypassSecurityPipe} from "../pipes/bypass-security.pipe";
import {ScriptService} from "../services/script.service";
import {MediaType} from "../pojo/media-type";


@Component({
  selector: 'app-embed',
  imports: [],
  templateUrl: './embed.component.html',
  styleUrl: './embed.component.scss'
})
export class EmbedComponent {

  safeHtml: SafeHtml = "";
  @Input() content = ""
  scriptSpec: Map<MediaType, string>;
 @Input() mediaType: MediaType = MediaType.UNKNOWN;

  constructor(private element: ElementRef,
              private sanitizer: DomSanitizer,
              private renderer: Renderer2,
              private scriptService: ScriptService) {
    this.scriptSpec = new Map<MediaType, string>();
    this.scriptSpec.set(MediaType.INSTAGRAM, "https://www.instagram.com/embed.js")
    this.scriptSpec.set(MediaType.TWITTER, "https://platform.twitter.com/widgets.js")
    this.scriptSpec.set(MediaType.REDDIT, "https://embed.reddit.com/widgets.js")
  }

  ngOnInit() {
    this.safeHtml = this.sanitizer.bypassSecurityTrustHtml(this.content);
    const jsPath = this.scriptSpec.get(this.mediaType)
    if (jsPath) {

      const script = this.scriptService.loadJsScript(this.renderer, this.element, jsPath)
      script.onload = (event: Event) => {
        console.log(event, "Script loaded", jsPath);
      }
      script.onerror = () => {
        console.log("Script could not be loaded", jsPath);
      }
    }
  }
}
