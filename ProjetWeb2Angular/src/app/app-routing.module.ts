import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './index/index.component';
import { AcceuilComponent } from './index/acceuil/acceuil.component';

const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'acceuil', component: AcceuilComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
