package com.toptunov.fakecontactsavalanche

import kotlin.random.Random

object FakeDataGenerator {
    
    private val firstNames = listOf(
        "James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda",
        "William", "Elizabeth", "David", "Barbara", "Richard", "Susan", "Joseph", "Jessica",
        "Thomas", "Sarah", "Christopher", "Karen", "Daniel", "Nancy", "Matthew", "Lisa",
        "Anthony", "Betty", "Mark", "Margaret", "Donald", "Sandra", "Steven", "Ashley",
        "Paul", "Kimberly", "Andrew", "Emily", "Joshua", "Donna", "Kenneth", "Michelle",
        "Kevin", "Carol", "Brian", "Amanda", "George", "Dorothy", "Timothy", "Melissa",
        "Ronald", "Deborah", "Edward", "Stephanie", "Jason", "Rebecca", "Jeffrey", "Sharon",
        "Ryan", "Laura", "Jacob", "Cynthia", "Gary", "Kathleen", "Nicholas", "Amy",
        "Eric", "Angela", "Jonathan", "Shirley", "Stephen", "Anna", "Larry", "Brenda"
    )
    
    private val lastNames = listOf(
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
        "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas",
        "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson", "White",
        "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson", "Walker", "Young",
        "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
        "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell",
        "Carter", "Roberts", "Gomez", "Phillips", "Evans", "Turner", "Diaz", "Parker",
        "Cruz", "Edwards", "Collins", "Reyes", "Stewart", "Morris", "Morales", "Murphy"
    )
    
    private val companies = listOf(
        "TechCorp", "Innovate Solutions", "Global Systems", "Digital Dynamics", "Quantum Industries",
        "NextGen Technologies", "Fusion Enterprises", "Vertex Corp", "Axiom Systems", "Pinnacle Group",
        "Catalyst Inc", "Horizon Technologies", "Stellar Solutions", "Momentum Corp", "Velocity Systems",
        "Nexus Enterprises", "Summit Industries", "Apex Corporation", "Prime Technologies", "Zenith Group",
        "Vanguard Systems", "Odyssey Corp", "Atlas Technologies", "Frontier Solutions", "Titan Industries",
        "Phoenix Enterprises", "Spectrum Corp", "Infinity Systems", "Meridian Group", "Eclipse Technologies"
    )
    
    private val jobTitles = listOf(
        "Software Engineer", "Product Manager", "Data Analyst", "UX Designer", "Marketing Manager",
        "Sales Representative", "Project Manager", "Business Analyst", "DevOps Engineer", "HR Manager",
        "Financial Analyst", "Customer Success Manager", "Operations Manager", "Quality Assurance Engineer", "Content Writer",
        "Accountant", "Legal Counsel", "Systems Administrator", "Network Engineer", "Database Administrator",
        "Frontend Developer", "Backend Developer", "Full Stack Developer", "Mobile Developer", "Security Analyst",
        "Research Scientist", "Technical Writer", "Business Development Manager", "Product Designer", "Solutions Architect"
    )
    
    fun generateFullName(): String {
        return "${firstNames.random()} ${lastNames.random()}"
    }
    
    fun generatePhoneNumber(): String {
        val areaCode = Random.nextInt(200, 1000)
        val prefix = Random.nextInt(200, 1000)
        val lineNumber = Random.nextInt(1000, 10000)
        return "+1-$areaCode-$prefix-$lineNumber"
    }
    
    fun generateCompany(): String {
        return companies.random()
    }
    
    fun generateJobTitle(): String {
        return jobTitles.random()
    }
}

