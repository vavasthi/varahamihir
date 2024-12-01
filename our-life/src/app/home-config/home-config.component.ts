import {Component, inject} from '@angular/core';
import {MatFabButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatTooltip} from "@angular/material/tooltip";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatDialog} from "@angular/material/dialog";
import {HomeConfigAddEmbedDialog} from "./home-config-add-embed-dialog";

@Component({
  selector: 'app-home-config',
  imports: [
    MatFabButton,
    MatIcon,
    MatTooltip,
    MatMenu,
    MatMenuItem,
    MatMenuTrigger
  ],
  templateUrl: './home-config.component.html',
  styleUrl: './home-config.component.scss'
})
export class HomeConfigComponent {
  readonly dialog = inject(MatDialog);

  openAddEmbedDialog() {
    this.dialog.open(HomeConfigAddEmbedDialog);
  }
}
