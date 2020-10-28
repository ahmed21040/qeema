import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MyServicesService } from '../my-services.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  username: string;
  password: string;
  email:String;
  constructor(private myServicesService: MyServicesService ,
    private router: Router,private formBuilder: FormBuilder,private toastr: ToastrService) { }


  ngOnInit(): void {
  }
  reponseData;
  register(){

    if(this.username!=null && this.username!=""){
      if(this.email!=null && this.email!=""){
          if(this.password!=null && this.password!=""){
            this.myServicesService.regiser(this.username,this.email,this.password).subscribe(data => {
              console.log("data ",data);
              this.reponseData=data;
              if(this.reponseData==true){
                this.router.navigate(['/login']);
              }else{
                this.toastr.error("Error","fail", {
                  timeOut: 3000,
                });
              }
              this.myServicesService.token=this.reponseData.access_token
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
          this.toastr.error("Error","email required", {
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
