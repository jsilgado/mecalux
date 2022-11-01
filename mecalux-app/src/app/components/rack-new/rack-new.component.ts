import { Component, OnInit } from '@angular/core';
import {Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Rack } from 'src/app/models/rack';
import { SelectItem } from 'src/app/models/select-item';
import { RackService } from 'src/app/services/rack.service';
import Swal from 'sweetalert2';

export interface DialogData {
  warehouseId: string;
}

@Component({
  selector: 'app-rack-new',
  templateUrl: './rack-new.component.html',
  styleUrls: ['./rack-new.component.css']
})
export class RackNewComponent{

  rack: Rack = new Rack ();

  rackTypes: SelectItem[] = [
    {value: 'A', viewValue: 'A'},
    {value: 'B', viewValue: 'B'},
    {value: 'C', viewValue: 'C'},
    {value: 'D', viewValue: 'D'},
  ];

  constructor(
    private rackService:RackService,
    public dialogRef: MatDialogRef<RackNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit() {
    this.saveRack();
  }

  saveRack() {
    this.rackService.newRack(this.data.warehouseId, this.rack).subscribe({
      complete: () => {
        Swal.fire('Rack created', '', 'success');
        this.dialogRef.close();
      }, // completeHandler
      error: data => {
        Swal.fire(
          data.error.message[0], '',
          'error'
        )}    // errorHandler
    })
  }

}
