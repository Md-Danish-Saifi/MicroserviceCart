import { Component, OnInit } from '@angular/core';
import { AppserviceService } from '../Services/appservice.service';

@Component({
  selector: 'app-addproduct',
  templateUrl: './addproduct.component.html',
  styleUrls: ['./addproduct.component.css']
})
export class AddproductComponent implements OnInit {

  constructor(private service:AppserviceService) { }
  info = {
    title:'',
    price:'',
    description:'',
    image:''
  }

  ngOnInit(): void {
  }

  addProd(){
    this.service.addProduct(this.info).subscribe(res=>{
      console.log('response is');

      this.info.title ='',
      this.info.price ='',
      this.info.description ='',
      this.info.image =''

    })
  }

}
