#[allow(unused)]
use std::io::stdin;
use std::{self, fs};

fn main() {
    let nums = ["one", "two", "three", "four", "five", "six", "seven", "eight","nine"];
    let lines = fs::read_to_string("src/in.txt").expect("failed read");
    println!("{}", lines);
    println!();
    let ans:u32 = lines
        .lines()
        .map(|s| {
            let mut st: String = s.into();
            for (i, num) in nums.iter().enumerate() { 
                st = st.replace(num, format!("{}{}{}", num.chars().next().unwrap(), (i+1).to_string().as_str(), num.chars().rev().next().unwrap()).as_str());
            }
            st
        })
        .map(|s| s.chars().filter(|c| c.is_numeric()).collect::<String>() )
        .map(|s| (10*s.chars().next().unwrap().to_digit(10).unwrap() + s.chars().last().unwrap().to_digit(10).unwrap()))
        .sum();

    println!("{}", ans)
        //.map(|s| s.parse::<i32>().expect("failed parse"))
}
