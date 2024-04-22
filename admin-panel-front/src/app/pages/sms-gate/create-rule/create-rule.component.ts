import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnDestroy,
  OnInit,
  ViewEncapsulation,
} from '@angular/core';
import { NgIf } from '@angular/common';
import { ActivatedRoute, Route, Router, RouterLink } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatDialog } from '@angular/material/dialog';
import { Subject } from 'rxjs';
import { SuccessDialogComponent } from 'app/shared/components/dialog/success-dialog.component';
import { CreateRuleService } from 'app/core/services/create-rule.service';
import { CreateRuleModel } from 'app/core/models/create-rule.model';

@Component({
  selector: 'create-rule',
  templateUrl: './create-rule.component.html',
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
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
    RouterLink,
  ],
})
export class CreateRuleComponent implements OnInit, OnDestroy {
  ruleId: number;
  createRuleModel: CreateRuleModel = new CreateRuleModel();
  ruleForm: FormGroup;
  private _unsubscribeAll: Subject<any> = new Subject<any>();

  constructor(
    private formBuilder: FormBuilder,
    private _createRuleService: CreateRuleService,
    private _changeDetectorRef: ChangeDetectorRef,
    private _activateRoute: ActivatedRoute,
    private _matDialog: MatDialog,
    private _router: Router
  ) {
  }

  ngOnInit(): void {
    this._activateRoute.paramMap.subscribe((params) => {
      this.ruleId = +params.get('id');
      if (this.ruleId) {
        this.getRuleInformation();
      }
    });

    // Create form
    this.ruleForm = this.createFormBuilder();

    this.ruleForm.get('smsOnly').valueChanges.subscribe((smsOnly) => {
      if (smsOnly) {
        this.ruleForm.get('count').setValue('');
        this.ruleForm.get('periodic').setValue('');
        this.ruleForm.get('count').disable();
        this.ruleForm.get('periodic').disable();
      } else {
        this.ruleForm.get('count').enable();
        this.ruleForm.get('periodic').enable();
      }
    });

    this.ruleForm.get('smsOn').valueChanges.subscribe((smsOn) => {
      if (smsOn) {
        this.ruleForm.get('smsOnly').setValue(false);
      }
    });
  }

  ngOnDestroy(): void {
    this._unsubscribeAll.next(null);
    this._unsubscribeAll.complete();
  }

  exclusiveCheckboxValidator(group: FormGroup): { [key: string]: any } | null {
    const smsOnly = group.get('smsOnly');
    const smsOn = group.get('smsOn');
    return smsOnly && smsOn && smsOnly.value && smsOn.value
      ? { exclusiveCheckboxes: true }
      : null;
  }

  onSubmit(): void {
    this._createRuleService.createRule(this.createRuleModel)
    .subscribe(
      (response) => {
        console.log('Form data sent successfully:', response);
        this.openSuccessDialog({message: 'Правило создано успешно', title: 'Успех'});
      },
      (error) => {
        this.openSuccessDialog({message: 'Что-то пошло не так', title: 'Побробуйте позже'});
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
    return this.formBuilder.group(
      {
        code: [this.createRuleModel.code, Validators.required],
        name: [this.createRuleModel.name, Validators.required],
        description: [this.createRuleModel.description, Validators.required],
        smsOnly: [this.createRuleModel.smsOnly],
        smsOn: [this.createRuleModel.smsOn],
        count: [{ value: '', disabled: true }, Validators.required],
        periodic: [{ value: '', disabled: true }, Validators.required],
      },
      { validator: this.exclusiveCheckboxValidator }
    );
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
  getRuleInformation(): void {
    this._createRuleService
      .getRuleInformation(this.ruleId.toString())
      .subscribe((res) => {
        if (res) {
          this.createRuleModel = res;
          this.ruleForm.patchValue({
            code: res.code,
            name: res.name,
            description: res.description,
            smsOnly: res.smsOnly,
            smsOn: res.smsOn,
            count: res.count,
            periodic: res.periodic,
          });
        }
      });
  }
}
