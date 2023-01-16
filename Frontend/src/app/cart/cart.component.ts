import { Component, OnInit } from '@angular/core';
import { AppserviceService } from '../Services/appservice.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  products: any = [];
  values: any = [1, 2, 3, 4, 5];
  total = 0

  constructor(private service: AppserviceService) {}

  ngOnInit(): void {
    this.getAllProd();
  }

  getAllProd() {
    this.service.getAllCartProduct().subscribe((res) => {
      if (res.Result == 'Success') {
        this.products = res.products;
      }
      this.countSubTotal();
    
    });
  }
  calculateAmount(e: any, id: any) {
    const prod = this.products.map((el: any) => {
      if (el.id === id) {
        return { ...el, totalPrice: el.price * e.target.value };
      } else {
        return { ...el };
      }
    });
    this.products = prod;
    this.countSubTotal();
  }

  removeFromCart(id: any) {
    this.service.deleteCartProduct(id).subscribe((res) => {
      if (res.Result == 'Success') {
        this.getAllProd();
      }
      console.log('response is', this.products);
    });
  }

  countSubTotal() {
    this.total = 0;
    for (let i = 0; i < this.products.length; i++) {
      this.total += this.products[i].totalPrice? this.products[i].totalPrice : this.products[i].price;
  }
  

  }
}
