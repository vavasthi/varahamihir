import { Component, ElementRef, Input, Renderer2 } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { BypassSecurityPipe } from "../pipes/bypass-security.pipe";

@Component({
  selector: 'app-embed',
  standalone: true,
  imports: [BypassSecurityPipe],
  templateUrl: './embed.component.html',
  styleUrl: './embed.component.scss'
})
export class EmbedComponent {

  @Input() safeHtml: SafeHtml = "";

  constructor(private element: ElementRef,
    private sanitizer: DomSanitizer,
    private renderer: Renderer2) {
  }
}
