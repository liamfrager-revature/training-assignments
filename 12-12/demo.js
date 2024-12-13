var message;
message = "This is a string";
console.log(message);
var input = "Hello my name is Liam It is nice to meet you".split(" ");
function multiplyAll(words, multiplier) {
    var multipliedNums = [];
    for (var _i = 0, words_1 = words; _i < words_1.length; _i++) {
        var word = words_1[_i];
        multipliedNums.push(word.length * multiplier);
    }
    var sum = 0; // Infered type.
    for (var _a = 0, multipliedNums_1 = multipliedNums; _a < multipliedNums_1.length; _a++) {
        var num = multipliedNums_1[_a];
        sum += num;
    }
    return sum;
}
console.log(multiplyAll(input, 5));
