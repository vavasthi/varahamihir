import { Equation } from "./equation";

export interface PageableEquation {
  content:Equation[];
  empty:boolean;
  first:boolean;
  last:boolean;
  size:number
}
