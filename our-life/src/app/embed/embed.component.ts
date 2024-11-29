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

  constructor(private element: ElementRef,
              private sanitizer: DomSanitizer,
              private renderer: Renderer2,
              private scriptService: ScriptService) {
    this.scriptSpec = new Map<MediaType, string>();
    this.scriptSpec.set(MediaType.INSTAGRAM, "https://www.instagram.com/embed.js")
    this.scriptSpec.set(MediaType.TWITTER, "https://platform.twitter.com/widgets.js")
    this.scriptSpec.set(MediaType.REDDIT, "https://embed.reddit.com/widgets.js")
    this.scriptSpec.set(MediaType.BLUESKY, "https://embed.bsky.app/static/embed.js")
    this.scriptSpec.set(MediaType.SUBSTACK, "https://substack.com/embedjs/embed.js")
  }

  ngOnInit() {
    var mediaType = MediaType.UNKNOWN;
    for (let key of this.scriptSpec.keys()) {
      var url = this.scriptSpec.get(key)
      if (url) {

        if (this.content.indexOf(url) >= 0) {
          mediaType = key;
        }
      }
    }
    this.safeHtml = this.sanitizer.bypassSecurityTrustHtml(this.content);
    const jsPath = this.scriptSpec.get(mediaType)
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
