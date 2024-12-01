import {Component} from '@angular/core';
import {RouterModule} from '@angular/router';
import {MatTableModule} from '@angular/material/table';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import {MatDialogModule} from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTooltipModule} from '@angular/material/tooltip';
import {LwdAppComponent} from "../lwd-app/lwd-app.component";

@Component({
    selector: 'app-lwd-app-home',
    templateUrl: './lwd-app-home.component.html',
    styleUrl: './lwd-app-home.component.scss',
  imports: [MatTableModule,
    MatIconModule,
    MatToolbarModule,
    MatDialogModule,
    MatButtonModule,
    MatTooltipModule,
    RouterModule,
    MatMenuModule,
    LwdAppComponent]
})
export class LwdAppHomeComponent {
}
