import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'success-dialog',
  standalone: true,
  template: `
  <div class="flex flex-col gap-y-3">
  <h2 mat-dialog-title class="text-xl text-center font-600">{{ data.title }}</h2>
  <div mat-dialog-content class="text-[16px] text-center font-600">{{ data.message }}</div>
  <div mat-dialog-actions>
    <button mat-button [mat-dialog-close]="true" class="mat-raised-button bg-blue-600 w-full text-white rounded-md p-4" >OK</button>
  </div>
  </div>
`,
  imports:[MatDialogModule]
})
export class SuccessDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<SuccessDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { message: string, title: string }
  ) {}
}
