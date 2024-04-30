import random
import math
import pygame

pygame.init()

winW = 400
winH = 400
window = pygame.display.set_mode((winW, winH))

pygame.display.set_caption("Bad Bunny")

currentScene = 0
# randomizes bunny spawn after clicked
rNum = round(random.randint(0, 3))
stillPlaying = False
# keeps track of level/difficulty
level = 1
# array that holds boolean values to let the program know that the code activated each level once already
levelCondition = [False, False, False, False]
# keeps track of total time in the khan window
gameTime = 0
# keeps track of time spent on the start screen
elapsedTime = 0
# timer in game
startTime = 0
# value that keeps the value of start time at the moment the game ends
stopTime = 0

# array for the bunny spawn locations
LOCATIONS = [(-485, (winH / 2) - 15), ((winW / 2) - 35, -490),
             ((winW * 2) + 22, (winH / 2) - 15), ((winW / 2) - 35, (winH * 2) + 70)]


class Carrot:
    def __init__(self, x: float, y: float, h: float):
        self.x = x
        self.y = y
        self.h = h
        self.hitbox = ((self.x + (self.h * 78 / 100), self.y - (self.h * -57 / 100)),
                       (self.h * 46 / 100, self.h * 70 / 100))

    # function that draws carrot
    def draw(self):
        debug = False
        if debug:
            pygame.draw.rect(window, (255, 0, 0), self.hitbox)

        # carrot
        pygame.draw.polygon(window, (123, 255, 0), ((self.x + (self.h * 90 / 100), self.y + (self.h * 60 / 100)),
                                                    (self.x + (self.h * 110 / 100), self.y + (self.h * 60 / 100)),
                                                    (self.x + (self.h * 100 / 100), self.y + (self.h * 80 / 100))))
        pygame.draw.polygon(window, (255, 132, 0), ((self.x + (self.h * 80 / 100), self.y + (self.h * 75 / 100)),
                                                    (self.x + (self.h * 120 / 100), self.y + (self.h * 75 / 100)),
                                                    (self.x + (self.h * 100 / 100), self.y + (self.h * 125 / 100))))
        return


# creates the carrot object used for the game
gameCarrot = Carrot(winW / 4, winH / 4, 100)


class Bunny:
    def __init__(self, x: float, y: float, h: float, eyeSize: float, eyeColor, smile: bool):
        self.x = x
        self.y = y
        self.h = h
        self.eyeSize = eyeSize
        self.eyeColor = eyeColor
        self.smile = smile
        self.speedX = 0
        self.speedY = 0
        self.hitbox = ((self.x + (self.h * 23 / 100), self.y - (self.h * 91 / 100)),
                       (self.h * 156 / 100, self.h * 238 / 100))

    def draw(self):
        # redraw hitbox
        self.hitbox = ((self.x + (self.h * 23 / 100), self.y - (self.h * 91 / 100)),
                       (self.h * 156 / 100, self.h * 238 / 100))

        debug = False
        if debug:
            pygame.draw.rect(window, (255, 0, 0), self.hitbox)

        # left ear
        pygame.draw.ellipse(window, (255, 255, 255), ((self.x + (self.h * 30 / 100), self.y - (self.h * 90 / 100)),
                                                      (self.h * 60 / 100, self.h * 120 / 100)))
        # right ear
        pygame.draw.ellipse(window, (255, 255, 255), ((self.x + (self.h * 110 / 100), self.y - (self.h * 90 / 100)),
                                                      (self.h * 60 / 100, self.h * 120 / 100)))
        # left ear outline
        pygame.draw.ellipse(window, (0, 0, 0), ((self.x + (self.h * 30 / 100), self.y - (self.h * 90 / 100)),
                                                (self.h * 60 / 100, self.h * 120 / 100)), 1)
        # right ear outline
        pygame.draw.ellipse(window, (0, 0, 0), ((self.x + (self.h * 110 / 100), self.y - (self.h * 90 / 100)),
                                                (self.h * 60 / 100, self.h * 120 / 100)), 1)

        # face
        pygame.draw.ellipse(window, (255, 255, 255), ((self.x + (self.h * 25 / 100), self.y + (self.h * -4 / 100)),
                                                      (self.h * 150 / 100, self.h * 150 / 100)))
        # face outline
        pygame.draw.ellipse(window, (0, 0, 0), ((self.x + (self.h * 25 / 100), self.y + (self.h * -4 / 100)),
                                                (self.h * 150 / 100, self.h * 150 / 100)), 1)

        # left eye
        pygame.draw.ellipse(window, self.eyeColor, ((self.x + (self.h * 40 / 100), self.y + (self.h * 20 / 100)),
                                                    (self.eyeSize * 17 / 100, self.eyeSize * 17 / 100)))
        # right eye
        pygame.draw.ellipse(window, self.eyeColor, ((self.x + (self.h * 100 / 100), self.y + (self.h * 20 / 100)),
                                                    (self.eyeSize * 17 / 100, self.eyeSize * 17 / 100)))
        # left eye outline
        pygame.draw.ellipse(window, (0, 0, 0), ((self.x + (self.h * 40 / 100), self.y + (self.h * 20 / 100)),
                                                (self.eyeSize * 17 / 100, self.eyeSize * 17 / 100)), 1)
        # right eye outline
        pygame.draw.ellipse(window, (0, 0, 0), ((self.x + (self.h * 100 / 100), self.y + (self.h * 20 / 100)),
                                                (self.eyeSize * 17 / 100, self.eyeSize * 17 / 100)), 1)

        # mouth
        pygame.draw.line(window, (0, 0, 0), (self.x + (self.h * 65 / 100), self.y + (self.h * 100 / 100)),
                         (self.x + (self.h * 135 / 100), self.y + (self.h * 100 / 100)), 2)

        if self.smile:
            # left smile
            pygame.draw.arc(window, (0, 0, 0), ((self.x + (self.h * 45 / 100), self.y + (self.h * 59 / 100)),
                                                (self.h * 50 / 100, self.h * 45 / 100)), 3.5, 5, 3)
            # right smile
            pygame.draw.arc(window, (0, 0, 0), ((self.x + (self.h * 110 / 100), self.y + (self.h * 59 / 100)),
                                                (self.h * 50 / 100, self.h * 45 / 100)), 4.5, 6, 3)

        # left tooth
        pygame.draw.rect(window, (0, 0, 0), ((self.x + (self.h * 85 / 100), self.y + (self.h * 100 / 100)),
                                             (self.h * 15 / 100, self.h * 22 / 100)), 2)
        # right tooth
        pygame.draw.rect(window, (0, 0, 0), ((self.x + (self.h * 100 / 100), self.y + (self.h * 100 / 100)),
                                             (self.h * 15 / 100, self.h * 22 / 100)), 2)
        return

    # get bunny to move positions
    def handleClick(self):
        # checks if mouse is clicked in the bunny boundaries
        global rNum
        mouseX = pygame.mouse.get_pos()[0]
        mouseY = pygame.mouse.get_pos()[1]
        if (self.x + 4 <= mouseX <= self.x + 66) and (self.y - 36 <= mouseY <= self.y + 55):
            rNum = round(random.randint(0, 3))

            # when the bunny is clicked, it goes to a random spawn point in the array LOCATIONS
            self.x = LOCATIONS[rNum][0]
            self.y = LOCATIONS[rNum][1]
        return

    def eatCarrot(self):
        if ((self.hitbox[0][0] + self.hitbox[1][0] >= gameCarrot.hitbox[0][0] and
                self.hitbox[0][0] <= gameCarrot.hitbox[0][0] + gameCarrot.hitbox[1][0]) and
                (self.hitbox[0][1] + self.hitbox[1][1] >= gameCarrot.hitbox[0][1]
                 and self.hitbox[0][1] <= gameCarrot.hitbox[0][1] + gameCarrot.hitbox[1][1])):
            global currentScene
            currentScene = 2
            global stillPlaying
            stillPlaying = False
            global stopTime
            stopTime = startTime
            return

    def movement(self):
        # conditions that check bunnies' x or y positions to change their speed directions
        if self.x + 66 < 0:
            if level < 5:
                self.speedX = random.randint(5, 10)
                self.speedY = 0
            else:
                self.speedX = random.randint(7, 14)
                self.speedY = 0
        elif self.x + 4 > 400:
            if level < 5:
                self.speedX = random.randint(-10, -5)
                self.speedY = 0
            else:
                self.speedX = random.randint(-14, -7)
                self.speedY = 0
        elif self.y + 55 < 0:
            if level < 5:
                self.speedY = random.randint(5, 10)
                self.speedX = 0
            else:
                self.speedY = random.randint(7, 14)
                self.speedX = 0
        elif self.y - 36 > 400:
            if level < 5:
                self.speedY = random.randint(-10, -5)
                self.speedX = 0
            else:
                self.speedY = random.randint(-14, -7)
                self.speedX = 0
        return


# an array that holds Bunny objects
gameBunnies = []

# makes bunny object for the Start Screen and Game Over screen
screenBunny = Bunny(winW / 3.5, winH / 2.5, 90, 325, (143, 13, 24), True)


# button class
class Button:
    def __init__(self, x: int, y: int, width: int, height: int, label: str, onClick):
        self.x = x
        self.y = y
        self.width = width
        self.height = height
        self.label = label
        self.onClick = onClick

    # draws button
    def draw(self):
        pygame.draw.rect(window, (255, 170, 0), ((self.x, self.y), (self.width, self.height)), 0, 5)
        font = pygame.font.SysFont(None, 25)
        text = font.render(self.label, True, (0, 0, 0))
        window.blit(text, (self.x + self.width / 6, self.y + self.height / 3))
        return

    # checks if the mouse is inside of the button coordinates when clicked
    def isMouseInside(self):
        mouseX = pygame.mouse.get_pos()[0]
        mouseY = pygame.mouse.get_pos()[1]
        return self.x < mouseX < (self.x + self.width) and self.y < mouseY < (self.y + self.height)

    # clicks inside
    def handleMouseClick(self):
        if self.isMouseInside():
            self.onClick()
        return


def onClickStartButton():
    global currentScene
    currentScene = 1
    global elapsedTime
    # sets elapsed time to value of gameTime at the moment of click
    elapsedTime = gameTime
    global stillPlaying
    stillPlaying = True
    return


# creates the start button object
startButton = Button(winW / 3, winH - 75, 140, 50, "Start Game", onClickStartButton)


def onClickRestartButton():
    global currentScene
    currentScene = 0
    return


# creates the start button object
restartButton = Button(winW - 175, winH - 75, 155, 50, "Back to Start", onClickRestartButton)


def mouseClicked():
    if currentScene == 0:
        startButton.handleMouseClick()
    elif currentScene == 1:
        # goes through the gameBunnies array to check if the bunny is clicked
        for j in range(len(gameBunnies)):
            gameBunnies[j].handleClick()
    elif currentScene == 2:
        restartButton.handleMouseClick()
    return

    # function that draws the Start screen


def drawStartScreen():
    window.fill((0, 0, 0))
    screenBunny.draw()
    font = pygame.font.SysFont(None, 40)
    text = font.render("Bad Bunny", True, (0, 255, 81))
    window.blit(text, (winW / 3, 10))

    font = pygame.font.SysFont(None, 20)
    text = font.render("click the bunnies to keep them away from the carrot", True, (0, 255, 81))
    window.blit(text, (40, 50))
    startButton.draw()

    return


# function that draws the Game Screen
def drawGameScreen():
    window.fill((0, 0, 0))

    # starts the timer at zero at the moment when the start button is clicked
    global startTime
    startTime = math.floor(pygame.time.get_ticks() / 1000) - elapsedTime

    # carrot
    gameCarrot.draw()

    # level display
    global level
    font = pygame.font.SysFont(None, 25)
    text = font.render("Level: " + str(level), True, (255, 255, 255))
    window.blit(text, (35, 20))

    text = font.render("Time: " + str(startTime), True, (255, 255, 255))
    window.blit(text, (300, 20))

    if startTime == 10:
        level = 2
    elif startTime == 20:
        level = 3
    elif startTime == 30:
        level = 4
    elif startTime == 40:
        level = 5

    global rNum
    # conditions that check the levels to add the bunnies
    if level == 1 and not levelCondition[0]:
        # Pushes a Bunny object in the gameBunnies array
        rNum = round(random.randint(0, 3))
        gameBunnies.append(Bunny(LOCATIONS[rNum][0], LOCATIONS[rNum][1], 35, 100, (143, 13, 24), False))
        levelCondition[0] = True
    elif level == 2 and not levelCondition[1]:
        # Pushes a Bunny object in the gameBunnies array
        rNum = round(random.randint(0, 3))
        gameBunnies.append(Bunny(LOCATIONS[rNum][0], LOCATIONS[rNum][1], 35, 100, (143, 13, 24), False))
        levelCondition[1] = True
    elif level == 3 and not levelCondition[2]:
        # Pushes a Bunny object in the gameBunnies array
        rNum = round(random.randint(0, 3))
        gameBunnies.append(Bunny(LOCATIONS[rNum][0], LOCATIONS[rNum][1], 35, 100, (143, 13, 24), False))
        levelCondition[2] = True
    elif level == 4 and not levelCondition[3]:
        # Pushes a Bunny object in the gameBunnies array
        rNum = round(random.randint(0, 3))
        gameBunnies.append(Bunny(LOCATIONS[rNum][0], LOCATIONS[rNum][1], 35, 100, (143, 13, 24), False))
        levelCondition[3] = True

    # for loop that goes through the gameBunnies array
    for i in range(len(gameBunnies)):
        # draws all the bunnies
        gameBunnies[i].draw()
        gameBunnies[i].movement()

        # increments the x and y values of the gameBunnies
        if stillPlaying:
            gameBunnies[i].x += gameBunnies[i].speedX
            gameBunnies[i].y += gameBunnies[i].speedY

        gameBunnies[i].eatCarrot()
    return


# function that draws the Game Over screen
def drawGameOverScreen():
    # resets all values to how they were originally
    global gameBunnies
    gameBunnies = []
    global level
    level = 1
    global levelCondition
    levelCondition = [False, False, False, False]

    window.fill((0, 0, 0))
    screenBunny.draw()

    font = pygame.font.SysFont(None, 50)
    text = font.render("GAME OVER", True, (255, 132, 0))
    window.blit(text, (winW / 4.25, 10))

    font = pygame.font.SysFont(None, 20)
    text = font.render("The bunnies ate your only food! Now you will starve to death!", True, (255, 132, 0))
    window.blit(text, (10, 50))

    font = pygame.font.SysFont(None, 25)
    text = font.render("You lasted " + str(stopTime) + " seconds.", True, (255, 132, 0))
    window.blit(text, (10, 340))

    restartButton.draw()
    return


# main loop to run game
run = True
while run:
    pygame.time.delay(50)

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            run = False
        elif event.type == pygame.MOUSEBUTTONDOWN:
            mouseClicked()

    if currentScene == 0:
        # sets gameTime to seconds since game started
        gameTime = math.floor(pygame.time.get_ticks() / 1000)
        drawStartScreen()
    elif currentScene == 1:
        drawGameScreen()
    elif currentScene == 2:
        drawGameOverScreen()

    # window.fill((0, 0, 0))
    pygame.display.update()

pygame.quit()
