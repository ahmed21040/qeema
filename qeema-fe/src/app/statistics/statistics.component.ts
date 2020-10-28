import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MyServicesService } from '../my-services.service';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit, OnDestroy {
  createdUser="";
  loginUser="";

  private stompClient = null;

  constructor(private myServicesService: MyServicesService
    ,private router: Router ,private formBuilder: FormBuilder,private toastr: ToastrService) { 
      console.log("token",this.myServicesService.token )
      if(this.myServicesService.token ==""){
        this.router.navigate(['/login']);
      }
    }

  reponseData;
  ngOnInit(): void {
    this.myServicesService.getUserStatistics(this.myServicesService.token).subscribe(data => {
      console.log("data ",data);
      this.reponseData=data;
      
      this.createdUser= this.reponseData.totalCreatedUser;
      this.loginUser= this.reponseData.toatalLoginUser;

      console.log("sta ", this.reponseData)

      this.connectToWebsocketWithStomp();
    },error=>{
      console.log("error ",error);
      this.toastr.error("Error",error, {
        timeOut: 3000,
      });
    });
  }

    /**
   * This method creates a WebSocket connection with server using a stomp js client
   */
  connectToWebsocketWithStomp() {
  
    
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);
    // this.stompClient.debug = null;

    
    this.stompClient.connect({}, (frame=> {
      this.onConnected();
    }));
  
  }

   onConnected() {

    this.stompClient.subscribe('/topic/statistics',(data)=>{
      this.onMessageReceived(data);
    } );  
}

onMessageReceived(payload) {
  let message = JSON.parse(payload.body);
  this.parseUserStatistics(message);
  console.log('message', message)
  
}
parseUserStatistics(data){
  this.createdUser= data.totalCreatedUser;
  this.loginUser= data.toatalLoginUser;
}

  logout(){

    this.myServicesService.logout(this.myServicesService.token).subscribe(data => {
      this.router.navigate(['/login']);
    });
  }

  ngOnDestroy() {
    this.myServicesService.logout(this.myServicesService.token).subscribe(data => {
      this.router.navigate(['/login']);
    });
  }

}
