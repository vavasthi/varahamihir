import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LwdAppComponent } from './lwd-app.component';

describe('LwdAppComponent', () => {
  let component: LwdAppComponent;
  let fixture: ComponentFixture<LwdAppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LwdAppComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LwdAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
