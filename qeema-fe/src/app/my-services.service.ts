import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class MyServicesService {


  private baseUrl = 'http://localhost:8080';
  token="";

  constructor(private http: HttpClient) { }

  getUserByEmail(email: String): Observable<any> {
    return this.http.get(`${this.baseUrl}/user/${email}`);
    // console.log('ok');
  }

  sendRequest(request): Observable<any> {
    let headers = new HttpHeaders();
   headers = headers.set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post(`${this.baseUrl}/trece`,request,{
      headers, responseType: 'text'
    });
    // console.log('ok');
  }

  login(username, password){
    let params = new URLSearchParams();
    params.append('username',username);
    params.append('password',password);    
    params.append('grant_type','password');
    // params.append('client_id','fooClientIdPassword');
    // let headers = new HttpHeaders();
    // // headers.set('Content-type','application/x-www-form-urlencoded; charset=utf-8');
    // console.log("p",btoa("web:test"));
    // headers.set('Authorization','Basic '+btoa("web:test"));
    const headers = {
      'Authorization': 'Basic ' + btoa("web:test"),
      'Content-type': 'application/x-www-form-urlencoded'
    }
    
    return this.http.post(this.baseUrl+'/oauth/token', 
      params.toString(), {
        headers
      });
      
  }

  regiser(username,email, password){
    let user={
      "username":username,
      "email":email,
      "password":password
    }
   
    // const headers = {
    //   'Authorization': 'Basic ' + btoa("web:test"),
    //   'Content-type': 'application/x-www-form-urlencoded'
    // }
    
    return this.http.post(this.baseUrl+'/registration/user', 
    user);
      
  }

  getUserStatistics(token){
    
    const headers = {
      'Authorization': 'Bearer ' + token
    }
    return this.http.get(this.baseUrl+'/statistics',{headers});
      
  }

  logout(token){
    
    const headers = {
      'Authorization': 'Bearer ' + token
    }
    return this.http.post(this.baseUrl+'/relogout',null,{headers});
      
  }


}
