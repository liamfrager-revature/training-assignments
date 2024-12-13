import { MyInterface } from "./module";

let message: string;
message = "This is a string";
console.log(message);

let input: string[] = "Hello my name is Liam It is nice to meet you".split(" ");

function multiplyAll(words:string[],  multiplier: number): number {
    let multipliedNums: number[] = [];
    for (const word of words) {
        multipliedNums.push(word.length * multiplier);
    }
    let sum = 0 // Infered type.
    for (const num of multipliedNums) {
        sum += num;
    }
    return sum;
}

console.log(multiplyAll(input, 5));