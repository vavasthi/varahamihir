import { Directive, ElementRef, Input, SimpleChanges } from '@angular/core';
import { Subject, take, takeUntil } from 'rxjs';
import { MathjaxService } from '../services/mathjax.service';

@Directive({
  selector: '[appMathjax]',
  standalone: true
})
export class MathjaxDirective {


  @Input() appMathjax? : string;
  private alive$ = new Subject<boolean>();
  private readonly el: HTMLElement;

  constructor(private mathjaxService: MathjaxService,
    private elementRef: ElementRef) {
    this.el = elementRef.nativeElement;
  }

  ngOnInit() {
    this.render();
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes && changes['appMathjax'] && changes['appMathjax'].currentValue) {
      this.render();
    }
  }

  private render() {
    this.mathjaxService.ready().pipe(
      take(1),
      takeUntil(this.alive$)
    ).subscribe(() => this.mathjaxService.render(this.el, this.appMathjax));
  }

  ngOnDestroy() {
    this.alive$.next(false);
  }
}
