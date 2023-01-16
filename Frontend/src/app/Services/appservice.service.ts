import { Injectable } from '@angular/core';
import { GlobalBaseurl } from './GlobalBaseUrl';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
  HttpParams,
} from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AppserviceService {
  private baseApiUrl = GlobalBaseurl.BaseURL;
  constructor(private http: HttpClient) {}
  header = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Cache-Control': 'no-cache',
      "responseType": 'json',
      "Authorization": 'dANdplbQ9e2ncUX7D4A1',
    }),
  };

  loginCustomer(data: any) {
    return (
      this.http
        .post<any>(this.baseApiUrl + 'login', data, this.header)
        .pipe(catchError(this.handleError))
    );
  }

  registerCustomer(data: any) {
    return (
      this.http
        .post<any>(this.baseApiUrl + 'register', data, this.header)
        .pipe(catchError(this.handleError))
    );
  }

  addProduct(data: any) {
    return (
      this.http
        .post<any>(this.baseApiUrl +'addproduct', data, this.header)
        .pipe(catchError(this.handleError))
    );
  }

  getAllProduct() {
    return (
      this.http
        .get<any>(this.baseApiUrl + 'allproduct', this.header)
        .pipe(catchError(this.handleError))
    );
  }

  addToCart(data: any) {
    return (
      this.http
        .post<any>(this.baseApiUrl + 'addtocart', data, this.header)
        .pipe(catchError(this.handleError))
    );
  }
  getAllCartProduct() {
    const uid: any = localStorage.getItem('id');
    const params = new HttpParams().set('userId', uid);
    return (
      this.http
        .get<any>(this.baseApiUrl + 'getmycart?'+params, this.header)
        .pipe(catchError(this.handleError))
    );
  }
  deleteCartProduct(id:any) {
    const params = new HttpParams().set('id', id);
    return (
      this.http
        .delete<any>(this.baseApiUrl + 'deletecartitem?' + params, this.header)
        .pipe(catchError(this.handleError))
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        'Backend returned code ${error.status}, ' +
          'body was: ${error.error}' +
          error
      );
    }
    return throwError(
      'Something bad happened; please try again later.' + JSON.stringify(error)
    );
  }
}
