import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Warehouse } from '../../models/warehouse';
import { WarehouseService } from '../../services/warehouse.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-warehouse-list',
  templateUrl: './warehouse-list.component.html',
  styleUrls: ['./warehouse-list.component.css']
})
export class WarehouseListComponent implements OnInit {

  name: String;
  warehouses: Warehouse[];

  displayedColumns: string[] = ['client', 'id', 'type', 'size', 'actions'];

  constructor(private warehouseService:WarehouseService, private router:Router) { }

  ngOnInit(): void {
    this.getWarehouses();
  }

  private getWarehouses(){
    this.warehouseService.getLstWarehouses().subscribe(data => {
      this.warehouses = data;
    });
  }

  private getWarehouse(uuid:string){
    this.warehouseService.getWarehouse(uuid).subscribe(data => {
    });
  }

  updateWarehouse(uuid:string){
    this.router.navigate(['update-warehouse', uuid]);
  }

  deleteWarehouse(uuid:string){

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
        this.warehouseService.deleteWarehouse(uuid).subscribe({
          complete: () => {
            Swal.fire(
              'Warehouse deleted',
              'The warehouse and their racks has been successfully removed.',
              'success'
            );
            this.getWarehouses();
          }, // completeHandler
          error: data => {
            console.log(data);
            Swal.fire(data.error.message[0], '', 'error')}    // errorHandler
      })
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.fire('Canceled', 'The warehouse continues in the database.', 'error');
      }
    });
  }


  detailWarehouse(uuid:string){
    console.log("detailWarehouse" );
    this.router.navigate(['detail-warehouse', uuid]);
  }

  goToLstWarehouses() {
    this.router.navigate(['/warehouses']);
  }

}
