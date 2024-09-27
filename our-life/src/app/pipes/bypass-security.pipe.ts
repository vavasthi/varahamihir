import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Pipe({
  name: 'bypassSecurity',
  standalone: true
})
export class BypassSecurityPipe implements PipeTransform {

  constructor(private sanitizer: DomSanitizer) {}

  transform (value: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(value);
}

}
