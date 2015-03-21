// Playground - noun: a place where people can play

import UIKit

var gradeLevel:Int

func mathByGrade(wantedAnswer: UInt32, gradeLevel: UInt32) -> String {
    let ranGrade = Int(arc4random_uniform(gradeLevel)) + 1
    if (ranGrade == 1){
        return addRandomizer(wantedAnswer)
    }
    else if (ranGrade == 2) {
        return subRandomizer(wantedAnswer)
    }
    else if (ranGrade == 3) {
        return mulRandomizer(wantedAnswer)
    }
    else if (ranGrade == 4 || ranGrade == 5) {
        return divRandomizer(wantedAnswer)
    }
    return "Make sure you're still in elementary school!"
}


////////////////////////
/// HELPER FUNCTIONS ///
////////////////////////

func addRandomizer(wantedAnswer: UInt32) -> String {
    let firstNum = Int(arc4random_uniform(wantedAnswer))

    return "\(firstNum)+\(wantedAnswer-firstNum)"
}

func subRandomizer(wantedAnswer: UInt32) -> String {
    let firstNum = Int(arc4random_uniform(wantedAnswer))
    
    return "\(wantedAnswer+firstNum)-\(firstNum)"
}

func mulRandomizer(wantedAnswer: UInt32) -> String {
    var factors = findFactors(wantedAnswer)
    var firstNum:Int = factors[Int(arc4random_uniform(UInt32(factors.count)))]
    return "\(firstNum)*\(Int(wantedAnswer)/firstNum)"
}

func divRandomizer(wantedAnswer: UInt32) -> String {
    var factors = findFactors(wantedAnswer)
    var firstNum:Int = factors[Int(arc4random_uniform(UInt32(factors.count)))]
    return "\(Int(wantedAnswer)*firstNum)/\(firstNum)"
}

func findFactors(wantedAnswer: UInt32) -> [Int] {
    var factors = [Int](count: 1, repeatedValue: 1)
    for var index = 2; index <= Int(wantedAnswer)/2+1; index++ {
        if (Int(wantedAnswer) % index == 0){
            factors.append(index)
        }
    }
    return factors
}

func test(){
    for index in 1...26 {
        for index2 in 1...5{
            mathByGrade(UInt32(index), UInt32(index2))
        }
    }
}

mathByGrade(2, 5)
mathByGrade(2, 5)
mathByGrade(2, 5)
mathByGrade(2, 5)
mathByGrade(2, 5)
mathByGrade(2, 5)






