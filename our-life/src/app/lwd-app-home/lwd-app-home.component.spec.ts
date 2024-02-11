import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LwdAppHomeComponent } from './lwd-app-home.component';

describe('LwdAppHomeComponent', () => {
  let component: LwdAppHomeComponent;
  let fixture: ComponentFixture<LwdAppHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LwdAppHomeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LwdAppHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
