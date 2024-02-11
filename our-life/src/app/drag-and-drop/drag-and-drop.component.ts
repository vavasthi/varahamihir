import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { MatProgressBarModule } from '@angular/material/progress-bar'
import { DragAndDropDirective } from '../directives/drag-and-drop.directive';

@Component({
  selector: 'app-drag-and-drop',
  standalone: true,
  imports: [MatProgressBarModule,
  DragAndDropDirective],
  templateUrl: './drag-and-drop.component.html',
  styleUrl: './drag-and-drop.component.scss'
})
export class DragAndDropComponent {
  @Input() multiple:boolean = false;

  @ViewChild("fileDropRef", { static: false }) fileDropEl?: ElementRef;
  @ViewChild("uploadedImage") uploadedImage? : ElementRef
  @Input() fileList?: FileList|null;
  @Output() fileSelected = new EventEmitter<any>();

  /**
   * on file drop handler
   */
  onFileDropped(event:any) {
    this.fileList = event;
    this.fileSelected.emit(event)
  }

  /**
   * handle file from browsing
   */
  fileBrowseHandler(event:Event) {
    const htmlEvent = event.currentTarget as HTMLInputElement;
    this.fileSelected.emit(htmlEvent.files)
    this.fileList = htmlEvent.files;
  }
}

