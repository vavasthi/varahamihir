import { Embed } from "./embed";

export interface PageableEmbed {
  content:Embed[];
  empty:boolean;
  first:boolean;
  last:boolean;
  size:number
}
