import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { NgIf } from '@angular/common';
import { ActivatedRoute, Route, Router, RouterLink } from '@angular/router';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatRadioModule} from '@angular/material/radio';
import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { Subject } from 'rxjs';
import { CreateNotificationService } from '../../../core/services/create-notification.service';
import { CreateNotificationModel } from '../../../core/models/create-notification.model';
import { MatDialog } from '@angular/material/dialog';
import { SuccessDialogComponent } from 'app/shared/components/dialog/success-dialog.component';

@Component({
  selector: 'create-notification',
  templateUrl: './create-notification.component.html',
  encapsulation  : ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone     : true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatCheckboxModule,
    MatRadioModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatButtonModule,
    MatButtonToggleModule,
    NgIf,
    RouterLink
  ],

})
export class CreateNotificationComponent implements OnInit, OnDestroy {
  noteId: number;
  createNoteModel: CreateNotificationModel = new CreateNotificationModel();
  noteForm: FormGroup;
  private _unsubscribeAll: Subject<any> = new Subject<any>();

  constructor(
    private formBuilder: FormBuilder,
    private _createNotificationService: CreateNotificationService,
    private _changeDetectorRef: ChangeDetectorRef,
    private _activateRoute: ActivatedRoute,
    private _matDialog: MatDialog,
    private _router: Router
  ) {
  }

  ngOnInit(): void {
    this._activateRoute.paramMap.subscribe((params) => {
      this.noteId = +params.get('id');
      if (this.noteId) {
        this.getNoteInformation();
      }
    });

    // Create form
    this.noteForm = this.createFormBuilder();

  }

  ngOnDestroy(): void {
    this._unsubscribeAll.next(null);
    this._unsubscribeAll.complete();
  }

  onSubmit(): void {
    this._createNotificationService.createNotification(this.createNoteModel)
    .subscribe(
      response => {
        console.log('Form data sent successfully:', response);
        this.openSuccessDialog({message: 'PUSH-уведомление создано успешно', title: 'Успех'});
      },
      error => {
        console.error('Error sending form data:', error);
      }
    );
  }

  openSuccessDialog(data): void {
    const dialogRef = this._matDialog.open(SuccessDialogComponent, {
      width: '350px',
      data: data 
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Закрыто');
      this._router.navigateByUrl(this._router.url);
    });
  }

  /**
   * Form builder
   * <hr><br>
   * 
   * <p>
   * Create form builder for rules in model
   * </p>
  */
  createFormBuilder(): FormGroup {
    return this.formBuilder.group({
      message: [this.createNoteModel.message, Validators.required],
      description: [this.createNoteModel.description, Validators.required],
      title: [this.createNoteModel.title, Validators.required],
      rule_code: [this.createNoteModel.rule_code, Validators.required],
      sent: [this.createNoteModel.sent]
    });
  }

  /**
   * Get rule information
   * <hr><br>
   * 
   * <p>
   * Get information about rule by unique id
   * </p>
   * 
  */
  getNoteInformation(): void {
    this._createNotificationService.getNoteInformation(this.noteId.toString())
    .subscribe((res) => {
      if (res) {
        this.createNoteModel = res;
        this.noteForm.patchValue({
          message: res.message,
          description: res.description,
          title: res.title,
          rule_code: res.rule_code,
          sent: res.sent
        });
      }
    }, error => {
    });
  }

}
