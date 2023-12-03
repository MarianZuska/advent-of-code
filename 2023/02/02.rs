#[allow(unused)]
use std::io::stdin;
use std::{self, fs};
use std::cmp::max;
use std::collections::HashMap;
use regex::Regex;


fn main() {
    let lines = fs::read_to_string("src/in.txt").expect("failed read");

    let mut sum: i32 = 0;
    for line in lines.lines() {
        let mut bag: HashMap<&str, i32> = HashMap::from([
            ("red", 0), 
            ("green", 0),
            ("blue",0)
        ]);

        //let (_, line) = line.split_once(": ").unwrap();
        // let pulls: Vec<&str> = line.split("; ").flat_map(|pull| pull.split(", ")).collect();
        
        let re: Regex = Regex::new(r"(?<amount>\d+) (?<name>\w+)").unwrap();
        for capture in re.captures_iter(line) {
            let name = &capture["name"];
            let amount = capture["amount"].parse().unwrap();
            if let Some(value) = bag.get_mut(name) { 
                *value = max(amount, *value);
            }
        }
        sum += bag.values().product::<i32>();
    }
    println!("{}", sum);
}
