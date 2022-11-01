import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Rack } from 'src/app/models/rack';
import { SelectItem } from 'src/app/models/select-item';
import { RackService } from 'src/app/services/rack.service';
import Swal from 'sweetalert2';
import { Warehouse } from '../../models/warehouse';
import { WarehouseService } from '../../services/warehouse.service';
import { RackNewComponent } from '../rack-new/rack-new.component';


@Component({
  selector: 'app-warehouse-form',
  templateUrl: './warehouse-form.component.html',
  styleUrls: ['./warehouse-form.component.css']
})
export class WarehouseFormComponent implements OnInit {

  id:string;
  warehouse: Warehouse = new Warehouse();
  mode:string;

  isNew:boolean;
  isUpdate:boolean;
  isDetail:boolean;

  warehouseFamilies: SelectItem[] = [
    {value: 'EST', viewValue: 'EST'},
    {value: 'ROB', viewValue: 'ROB'},
  ];

  clientFormControl = new FormControl('', [Validators.required]);
  sizeFormControl = new FormControl('', [Validators.required]);

  racks:Rack[];

  displayedColumns: string[] = ['id', 'type', 'actions'];

  htmlContentHeader = '';

  constructor(private warehouseService:WarehouseService,
    private rackService:RackService,
    public dialog: MatDialog,
    private activatedRoute:ActivatedRoute,
    private router:Router) { }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.setMode();

    if (this.isDetail || this.isUpdate) {
      this.warehouseService.getWarehouse(this.id).subscribe(data => {
        this.warehouse = data;
      });
      this.rackService.getRacksWarehouse(this.id).subscribe(data => {
        this.racks = data;
      });
    }
  }


  setMode() {

    if (this.activatedRoute.snapshot.url[0].path.includes('new')) {
      this.isNew = true;
      this.htmlContentHeader = '<h1 class="text-center">New warehouse</h1>';
    } else if (this.activatedRoute.snapshot.url[0].path.includes('update')) {
      this.isUpdate = true;
      this.htmlContentHeader = '<h1 class="text-center">Edit warehouse</h1>';
    } else if (this.activatedRoute.snapshot.url[0].path.includes('detail')) {
      this.htmlContentHeader = '<h1 class="text-center">Detail warehouse</h1>';
      this.isDetail = true;
    }
  }

  saveWarehouse() {
    this.warehouseService.newWarehouse(this.warehouse).subscribe({
      complete: () => { this.goToLstWarehouses();
        Swal.fire(
          'Warehouse created',
          'The warehouse has been successfully created.',
          'success'
        )
      }, // completeHandler
      error:  data => {
        Swal.fire(data.error.message[0], '', 'error')}    // errorHandler
    })
  }

  updateWarehouse() {
    this.warehouseService.updateWarehouse(this.id, this.warehouse).subscribe({
      complete: () => {
        Swal.fire('Warehouse updated', 'The warehouse has been successfully updated.','success'
      ), this.goToLstWarehouses(); }, // completeHandler
      error: data => { Swal.fire(data.error.message[0], '', 'error')}    // errorHandler
    })
  }

  goToLstWarehouses() {
    this.router.navigate(['/warehouses']);
  }

  onSubmit() {
    if (this.isNew) {
      this.saveWarehouse();
    }
    if (this.isUpdate) {
      this.updateWarehouse();
    }

  }

  openDialog(): void {
    const dialogRef = this.dialog.open(RackNewComponent, {
      width: '500px',
      data: {warehouseId: this.warehouse.id},
    });

    dialogRef.afterClosed().subscribe(() => {
      this.rackService.getRacksWarehouse(this.id).subscribe(data => {
        this.racks = data;
      });
    });
  }

  deleteRack(uuid:string){

    Swal.fire({
      title: '¿Are you sure?',
      text: "Confirm if you want to delete.",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete',
      cancelButtonText: 'No, cancel',
    }).then((result) => {
      if (result.value) {
        this.rackService.deleteRack(uuid).subscribe({
          complete: () => {
            Swal.fire('Rack deleted', 'The rack has been successfully removed.', 'success');
            this.rackService.getRacksWarehouse(this.id).subscribe(data => {
              this.racks = data;
            });
          }, // completeHandler
          error: data => {
            console.log(data);
            Swal.fire(data.error.message[0], '', 'error')
          }    // errorHandler
      })
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.fire('Canceled', 'The rack continues in the database.', 'error');
      }
    });
  }

}
