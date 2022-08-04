formContainer = document.createElement("div");
form = document.createElement("div");
label = document.createElement("label");
input = document.createElement("input");
button = document.createElement("button");
dayLabel = document.createElement("h2");
dayLabel.innerText = "Adding Item to: ";
dayLabel.innerText += document.getElementById("monthName").innerText + " ";
day = document.createElement("span");
day.id = "eventFormDay";
dayLabel.appendChild(day);
form.appendChild(dayLabel);
label.innerText = "Event description: ";
form.appendChild(label);
input.placeholder = "Work: 09:00 - 17:00";
input.id = "eventInput";
form.appendChild(input);
button.innerText = "add event";
button.id = "eventSubmit";
form.appendChild(button);
form.classList.add("eventForm");
formContainer.id = "eventFormContainer";
formContainer.appendChild(form);
formContainer.classList.add("no-print");
document.querySelector("body").appendChild(formContainer);
formContainer = document.getElementById("eventFormContainer");
formContainer.style.display = "none";

table = document.querySelector("table");

selectedDay = null;

formContainer.onclick = function(e){
	if(e.target.id == "eventFormContainer"){
		formContainer.style.display = "none";
	}
}

button.onclick = function(e){
	dayChildren = selectedDay.children;
	appendArea = null;
	for(i = 0; i < dayChildren.length; i++){
		if(dayChildren[i].classList.contains("eventAppendArea")){
			appendArea = dayChildren[i];
		}
	}
	eventHolder = document.createElement("div");
	eventHolder.classList.add("event");
	eventText = document.createElement("p");
	eventText.innerText = input.value;
	input.value = "";
	removeEvent = document.createElement("p");
	removeEvent.classList.add("remove-event");
	removeEvent.innerText="x";
	eventHolder.appendChild(eventText);
	eventHolder.appendChild(removeEvent);
	appendArea.appendChild(eventHolder);
	formContainer.style.display = "none";
}

table.onclick = function(e){
	console.log(e.target);
	clicked = e.target;
	if(clicked.classList.contains("addEvent")){
		formContainer.style.display = "flex";
		calendarDay = clicked.parentElement;
		selectedDay = calendarDay;
		dayChildren = calendarDay.children;
		for(i = 0; i < dayChildren.length; i++){
			if(dayChildren[i].classList.contains("dayNumber")){
				document.getElementById("eventFormDay").innerText = dayChildren[i].innerText;
			}
		}
	}
	if(clicked.classList.contains("remove-event")){
		clicked.parentElement.remove();
	}
};
