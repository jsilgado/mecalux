import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RackNewComponent } from './rack-new.component';

describe('RackNewComponent', () => {
  let component: RackNewComponent;
  let fixture: ComponentFixture<RackNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RackNewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RackNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
