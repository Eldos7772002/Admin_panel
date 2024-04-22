import { CurrencyPipe, NgClass, NgFor, NgIf } from '@angular/common';
import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnDestroy,
  OnInit,
  ViewEncapsulation,
  AfterViewInit,
  ViewChild,
} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatRippleModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { Router, RouterLink } from '@angular/router';
import { TranslocoModule } from '@ngneat/transloco';
import { Subject, map, takeUntil } from 'rxjs';
import { SmsGateService } from '../../core/services/sms-gate.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'sms-gate',
  templateUrl: './sms-gate.component.html',
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
  imports: [
    HttpClientModule,
    TranslocoModule,
    MatIconModule,
    MatButtonModule,
    MatRippleModule,
    MatMenuModule,
    MatTabsModule,
    MatButtonToggleModule,
    NgFor,
    NgIf,
    MatTableModule,
    NgClass,
    CurrencyPipe,
    RouterLink,
    MatPaginatorModule,
  ],
})
export class SmsGateComponent implements OnInit, OnDestroy, AfterViewInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;

  data: any;
  rulesData: any;
  notificationData: any;
  logData: any;
  logsPaginationData: MatTableDataSource<any>;
  private _unsubscribeAll: Subject<any> = new Subject<any>();

  displayedRulesColumns: string[] = [
    'id',
    'code',
    'priority',
    'name',
    'description',
    'sms-only',
    'sms-on',
    'count',
    'periodic',
    'action',
  ];
  displayedLogColumns: string[] = [
    'id',
    'created',
    'sendDate',
    'smsId',
    'smsText',
    'smsCount',
    'statusCode',
    'phoneNumber',
    'dateCompleted',
    'barcode',
    'messageType',
    'userName',
    'userId',
  ];  
  displayedNotificationColumns: string[] = [
    'id',
    'message',
    'description',
    'title',
    'rule_code',
    'sent',
    'sentAt',
    'createdAt',
    'noteAction',
  ];

  logsColumns = [
    { colName: 'id', displayText: 'ID', dataProperty: 'ID' },
    { colName: 'created', displayText: 'CREATED', dataProperty: 'CREATED' },
    {
      colName: 'sendDate',
      displayText: 'SEND DATE',
      dataProperty: 'SEND_DATE',
    },
    { colName: 'smsId', displayText: 'SMS ID', dataProperty: 'SMS_ID' },
    { colName: 'smsText', displayText: 'SMS TEXT', dataProperty: 'SMS_TEXT' },
    {
      colName: 'smsCount',
      displayText: 'SMS COUNT',
      dataProperty: 'SMS_COUNT',
    },
    {
      colName: 'statusCode',
      displayText: 'STATUS CODE',
      dataProperty: 'STATUS_CODE',
    },
    {
      colName: 'phoneNumber',
      displayText: 'PHONE NUMBER',
      dataProperty: 'PHONE_NUMBER',
    },
    {
      colName: 'dateCompleted',
      displayText: 'DATE COMPLETED',
      dataProperty: 'DATE_COMPLETED',
    },
    {
      colName: 'statusCodeCompleted',
      displayText: 'STATUS CODE COMPLETED',
      dataProperty: 'STATUS_CODE_COMPLETED',
    },
    { colName: 'barcode', displayText: 'BARCODE', dataProperty: 'barcode' },
    {
      colName: 'messageType',
      displayText: 'MESSAGE TYPE',
      dataProperty: 'MESSAGE_TYPE',
    },
    {
      colName: 'userName',
      displayText: 'USER NAME',
      dataProperty: 'USER_NAME',
    },
    { colName: 'userId', displayText: 'USER ID', dataProperty: 'USER_ID' },
  ];
  rulesColumns = [
    { colName: 'id', displayText: 'ID', dataProperty: 'id' },
    { colName: 'code', displayText: 'Код', dataProperty: 'code' },
    { colName: 'priority', displayText: 'Приоритет', dataProperty: 'priority' },
    { colName: 'name', displayText: 'Имя', dataProperty: 'name' },
    {
      colName: 'description',
      displayText: 'Описание',
      dataProperty: 'description',
    },
    { colName: 'sms-only', displayText: 'Только SMS', dataProperty: 'smsOnly' },
    { colName: 'sms-on', displayText: 'SMS Вкл.', dataProperty: 'smsOn' },
    { colName: 'count', displayText: 'Количество', dataProperty: 'count' },
    {
      colName: 'periodic',
      displayText: 'Периодичность',
      dataProperty: 'periodic',
    },
  ];

  notificationColumns = [
    { colName: 'id', displayText: 'ID', dataProperty: 'id' },
    { colName: 'message', displayText: 'Сообщение', dataProperty: 'message' },
    {
      colName: 'description',
      displayText: 'Описание',
      dataProperty: 'description',
    },
    { colName: 'title', displayText: 'Название', dataProperty: 'title' },
    {
      colName: 'rule_code',
      displayText: 'Код правила',
      dataProperty: 'rule_code',
    },
    { colName: 'sent', displayText: 'Отправлено', dataProperty: 'sent' },
    {
      colName: 'sentAt',
      displayText: 'Время отправки',
      dataProperty: 'sentAt',
    },
    { colName: 'createdAt', displayText: 'Создано', dataProperty: 'createdAt' },
  ];

  /**
   * Constructor
   */
  constructor(
    private _smsGateService: SmsGateService,
    private _router: Router,
    private _changeDetectorRef: ChangeDetectorRef
  ) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  /**
   * On init
   */
  ngOnInit(): void {
    this._smsGateService
      .getRulesData()
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe((data) => {
        this.rulesData = data;

        this._changeDetectorRef.detectChanges();
      });

    this._smsGateService
      .getNotificationsInfo()
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe((res) => {
        this.notificationData = res;
        this._changeDetectorRef.detectChanges();
      });

    this._smsGateService
      .getLogData()
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe((logs) => {
        this.logData = logs;
        this.logsPaginationData = new MatTableDataSource(logs);
        // this.logsPaginationData.paginator = this.paginator;

        this._changeDetectorRef.detectChanges();
      });
  }

  ngAfterViewInit() {
    // this.logsPaginationData.paginator = this.paginator;
  }

  /**
   * On destroy
   */
  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next(null);
    this._unsubscribeAll.complete();
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * Track by function for ngFor loops
   *
   * @param index
   * @param item
   */
  trackByFn(index: number, item: any): any {
    return item.id || index;
  }

  deleteRule(ruleId: string): void {
    this._smsGateService
      .deleteRule(ruleId)
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe((res) => {
        // if (res.status === 200) {
          // Reload the current route
          this._router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this._router.navigate([this._router.url]);
          });
        // }
          this._changeDetectorRef.detectChanges();
      });
  }

  deleteNotification(noteId: string): void {
    this._smsGateService
      .deleteNotification(noteId)
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe((res) => {
        // if (res.status === 200) {
          // Reload the current route
          this._router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this._router.navigate([this._router.url]);
          });
        // }
          this._changeDetectorRef.detectChanges();
      });
  }
}
