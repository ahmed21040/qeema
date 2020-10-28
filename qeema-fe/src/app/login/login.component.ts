import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MyServicesService } from '../my-services.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email: string;
  username: string;
  password: string;
  requestTitle:string;
  curUserObject=null;
  welcomeMsg='';

  constructor(private myServicesService: MyServicesService
    ,private router: Router ,private formBuilder: FormBuilder,private toastr: ToastrService) { }


  ngOnInit(): void {
  }

  reponseData:any;
  login(){
    // this.email="test"
    // this.connectToWebsocketWithStomp();
    if(this.username!=null && this.username!=""){

      if(this.password!=null && this.password!=""){
        this.myServicesService.login(this.username,this.password).subscribe(data => {
          console.log("data ",data);
          this.reponseData=data;
          this.myServicesService.token=this.reponseData.access_token
          this.router.navigate(['/statistics']);
        },error=>{
          console.log("error ",error);
          this.toastr.error("Error",error, {
            timeOut: 3000,
          });
        });
      }else{
        this.toastr.error("Error","password required", {
          timeOut: 3000,
        });
      }

    }else{
      this.toastr.error("Error","Username required", {
        timeOut: 3000,
      });
    }
  }

}
