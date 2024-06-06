import { ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MallsService } from '../../../core/services/mall.service';
import {OutletsService} from "../../../core/services/outlet.service";
import {StorageService} from "../../../core/services/storage.service";

@Component({
  selector: 'outlet',
  templateUrl: './outlet.component.html',
  styleUrls: ['./outlet.component.css'],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule,
  ]
})
export class OutletComponent implements OnInit {
  outletForm: FormGroup;
  selectedFile: File | null = null;

  constructor(
    private fb: FormBuilder,
    private mallsService: MallsService,
    private outletsService: OutletsService,
    private storageService: StorageService
  ) {}

  ngOnInit() {
    this.initializeForm();
  }

  initializeForm() {
    this.outletForm = this.fb.group({
      mallId: [2, Validators.required],
      latitude1: [43.26371, Validators.required],
      longitude1: [76.92905, Validators.required],
      latitude2: [43.26338, Validators.required],
      longitude2: [76.92943, Validators.required],
      category: ['Fashion', Validators.required],
      name: ['Pull & Bear', Validators.required],
      defaultNotification: ['Добро пожаловать в магазин Pull & Bear', Validators.required],
      imageUrl: ['akbfkjasbfkjabf', Validators.required]
    });
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onSubmit() {
    if (this.outletForm.valid && this.selectedFile) {
      this.storageService.uploadImage(this.selectedFile).subscribe(downloadUrl => {
        const outletData = {
          mall: { id: this.outletForm.value.mallId },
          latitude1: this.outletForm.value.latitude1,
          longitude1: this.outletForm.value.longitude1,
          latitude2: this.outletForm.value.latitude2,
          longitude2: this.outletForm.value.longitude2,
          category: this.outletForm.value.category,
          name: this.outletForm.value.name,
          defaultNotification: this.outletForm.value.defaultNotification,
          imageUrl: downloadUrl
        };

        this.outletsService.createOutlets(outletData).subscribe(response => {
          // Handle response
          console.log('Outlet created:', response);
        });
      });
    }
  }
}
