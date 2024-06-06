import {ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {MallsService} from "../../core/services/mall.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";



@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./mall-form.component.css'],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule,


  ]
})
export class DashboardComponent implements OnInit {
  constructor(private mallsService: MallsService) {}
  map: any;
  mallName: string;
  latitude1: string;
  longitude1: string;
  latitude2: string;
  longitude2: string;
  defaultNotification: string;

  // Этот метод используется, когда вы хотите изменить стандартное поведение маршрутизатора (например, добавить анимацию между маршрутами).
  prepareRoute(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData && outlet.activatedRouteData['animation'];
  }

  ngOnInit() {
  }

  createMall() {
    const mallData = {
      name: this.mallName,
      latitude1: this.latitude1,
      longitude1: this.longitude1,
      latitude2: this.latitude2,
      longitude2: this.longitude2,
      defaultNotification: this.defaultNotification
    };

    this.mallsService.createMall(mallData).subscribe(
      (mall: any) => {
        console.log('Mall created successfully:', mall);
        // здесь вы можете выполнить какие-то действия после успешного создания магазина
      },
      error => {
        console.error('Error creating mall:', error);
        // здесь вы можете выполнить какие-то действия в случае ошибки
      }
    );
  }
}
