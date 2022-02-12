/*

  Shape Shifter
  =============
  A canvas experiment by Kenneth Cachia
  http://www.kennethcachia.com

  Updated code
  ------------
  https://github.com/kennethcachia/Shape-Shifter

*/

const S = {
  init($canvas, text = "") {
    if (!text) {
      console.warn("text params must!");
      return;
    }
    S.$canvas = $canvas;
    S.$text = text;
    S.Drawing.init($canvas);
    S.UI.simulate(text);
    // S.UI.simulate('Shape|Shifter|Type|to start|#rectangle|#countdown 3||');

    S.Drawing.loop(() => {
      S.Shape.render();
    });
  },
  onresize() {
    // S.init(S.$canvas,S.$text);
    S.Drawing.init(S.$canvas);
    S.UI.simulate(S.$text);
    S.Shape.render();
  },
  onResize() {
    window.addEventListener("resize", S.onresize);
  },
  unOnResize() {
    window.removeEventListener("resize", S.onresize);
  },
};

S.Drawing = (function () {
  let canvas;
  let context;
  let renderFn;
  const requestFrame =
    window.requestAnimationFrame ||
    window.webkitRequestAnimationFrame ||
    window.mozRequestAnimationFrame ||
    window.oRequestAnimationFrame ||
    window.msRequestAnimationFrame ||
    function (callback) {
      window.setTimeout(callback, 1000 / 60);
    };

  return {
    init($el) {
      canvas = $el;
      context = canvas.getContext("2d");
      this.adjustCanvas();
    },

    loop(fn) {
      renderFn = !renderFn ? fn : renderFn;
      this.clearFrame();
      renderFn();
      requestFrame.call(window, this.loop.bind(this));
    },

    adjustCanvas() {
      // const width = Math.ceil(window.innerWidth / 2) * 2;
      // const height = Math.ceil(window.innerHeight / 2) * 2;
      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;
    },

    clearFrame() {
      context.clearRect(0, 0, canvas.width, canvas.height);
    },

    getArea() {
      return { w: canvas.width, h: canvas.height };
    },
    // 画圆
    drawCircle(p, c) {
      context.fillStyle = c.render();
      context.beginPath();
      context.arc(p.x, p.y, p.z / 1.2, 0, 2 * Math.PI, true);
      context.closePath();
      context.fill();
    },
  };
})();

S.UI = (function () {
  const input = document.querySelector(".ui-input");
  const ui = document.querySelector(".ui");
  const help = document.querySelector(".help");
  const commands = document.querySelector(".commands");
  const overlay = document.querySelector(".overlay");
  const canvas = document.querySelector(".canvas");
  let interval;
  const isTouch = false; // ('ontouchstart' in window || navigator.msMaxTouchPoints),
  let currentAction;
  // let resizeTimer;
  let time;
  const maxShapeSize = 30;
  let firstAction = true;
  let sequence = [];
  const cmd = "#";

  function formatTime(date) {
    const h = date.getHours();
    let m = date.getMinutes();
    m = m < 10 ? "0" + m : m;
    return h + ":" + m;
  }

  function getValue(value) {
    return value && value.split(" ")[1];
  }

  function getAction(value) {
    value = value && value.split(" ")[0];
    return value && value[0] === cmd && value.substring(1);
  }

  function timedAction(fn, delay, max, reverse) {
    clearInterval(interval);
    currentAction = reverse ? max : 1;
    fn(currentAction);

    if (
      !max ||
      (!reverse && currentAction < max) ||
      (reverse && currentAction > 0)
    ) {
      interval = setInterval(() => {
        currentAction = reverse ? currentAction - 1 : currentAction + 1;
        fn(currentAction);

        if (
          (!reverse && max && currentAction === max) ||
          (reverse && currentAction === 0)
        ) {
          clearInterval(interval);
        }
      }, delay);
    }
  }

  function reset(destroy) {
    clearInterval(interval);
    sequence = [];
    time = null;
    destroy && S.Shape.switchShape(S.ShapeBuilder.letter(""));
  }

  function performAction(value) {
    let action;
    let current;

    // overlay.classList.remove('overlay--visible');
    sequence =
      typeof value === "object" ? value : sequence.concat(value.split("|"));
    // input.value = '';
    // checkInputWidth();

    timedAction(
      () => {
        current = sequence.shift();
        action = getAction(current);
        value = getValue(current);
        let t = formatTime(new Date());

        switch (action) {
          case "countdown":
            value = parseInt(value, 10) || 10;
            value = value > 0 ? value : 10;

            timedAction(
              (index) => {
                if (index === 0) {
                  if (sequence.length === 0) {
                    S.Shape.switchShape(S.ShapeBuilder.letter(""));
                  } else {
                    performAction(sequence);
                  }
                } else {
                  S.Shape.switchShape(S.ShapeBuilder.letter(index), true);
                }
              },
              1000,
              value,
              true
            );
            break;

          case "rectangle":
            value = value && value.split("x");
            value =
              value && value.length === 2
                ? value
                : [maxShapeSize, maxShapeSize / 2];

            S.Shape.switchShape(
              S.ShapeBuilder.rectangle(
                Math.min(maxShapeSize, parseInt(value[0], 10)),
                Math.min(maxShapeSize, parseInt(value[1], 10))
              )
            );
            break;

          case "circle":
            value = parseInt(value, 10) || maxShapeSize;
            value = Math.min(value, maxShapeSize);
            S.Shape.switchShape(S.ShapeBuilder.circle(value));
            break;

          case "time":
            if (sequence.length > 0) {
              S.Shape.switchShape(S.ShapeBuilder.letter(t));
            } else {
              timedAction(() => {
                t = formatTime(new Date());
                if (t !== time) {
                  time = t;
                  S.Shape.switchShape(S.ShapeBuilder.letter(time));
                }
              }, 1000);
            }
            break;

          default:
            S.Shape.switchShape(
              S.ShapeBuilder.letter(current[0] === cmd ? "What?" : current)
            );
        }
      },
      2000,
      sequence.length
    );
  }

  function checkInputWidth() {
    if (input.value.length > 18) {
      ui.classList.add("ui--wide");
    } else {
      ui.classList.remove("ui--wide");
    }

    if (firstAction && input.value.length > 0) {
      ui.classList.add("ui--enter");
    } else {
      ui.classList.remove("ui--enter");
    }
  }

  function bindEvents() {
    document.body.addEventListener("keydown", (e) => {
      input.focus();

      if (e.keyCode === 13) {
        firstAction = false;
        reset();
        performAction(input.value);
      }
    });

    input.addEventListener("input", checkInputWidth);
    input.addEventListener("change", checkInputWidth);
    input.addEventListener("focus", checkInputWidth);

    help.addEventListener("click", () => {
      overlay.classList.toggle("overlay--visible");
      overlay.classList.contains("overlay--visible") && reset(true);
    });

    commands.addEventListener("click", (e) => {
      let el;

      if (e.target.classList.contains("commands-item")) {
        el = e.target;
      } else {
        el = e.target.parentNode.classList.contains("commands-item")
          ? e.target.parentNode
          : e.target.parentNode.parentNode;
      }

      const info = el && el.querySelector(".commands-item-info");
      const demo = el && info.getAttribute("data-demo");
      const url = el && info.getAttribute("data-url");

      if (info) {
        overlay.classList.remove("overlay--visible");

        if (demo) {
          input.value = demo;

          if (isTouch) {
            reset();
            performAction(input.value);
          } else {
            input.focus();
          }
        } else if (url) {
          // window.location = url;
        }
      }
    });

    canvas.addEventListener("click", () => {
      overlay.classList.remove("overlay--visible");
    });
  }

  /* eslint-disable */
  function init() {
    bindEvents();
    input.focus();
    isTouch && document.body.classList.add("touch");
  }

  // Init
  // init();

  return {
    simulate(action) {
      performAction(action);
    },
  };
})();

// S.UI.Tabs = (function () {
//   var tabs = document.querySelector('.tabs'),
//     labels = document.querySelector('.tabs-labels'),
//     triggers = document.querySelectorAll('.tabs-label'),
//     panels = document.querySelectorAll('.tabs-panel');
//
//   function activate(i) {
//     triggers[i].classList.add('tabs-label--active');
//     panels[i].classList.add('tabs-panel--active');
//   }
//
//   function bindEvents() {
//     labels.addEventListener('click', function (e) {
//       var el = e.target,
//         index;
//
//       if (el.classList.contains('tabs-label')) {
//         for (var t = 0; t < triggers.length; t++) {
//           triggers[t].classList.remove('tabs-label--active');
//           panels[t].classList.remove('tabs-panel--active');
//
//           if (el === triggers[t]) {
//             index = t;
//           }
//         }
//
//         activate(index);
//       }
//     });
//   }
//
//   function init() {
//     activate(0);
//     bindEvents();
//   }
//
//   // Init
//   init();
// }());

S.Point = function (args) {
  this.x = args.x;
  this.y = args.y;
  this.z = args.z;
  this.a = args.a;
  this.h = args.h;
};

S.Color = function (r, g, b, a) {
  this.r = r;
  this.g = g;
  this.b = b;
  this.a = a;
};

S.Color.prototype = {
  render() {
    return "rgba(" + this.r + "," + +this.g + "," + this.b + "," + this.a + ")";
  },
};

S.Dot = function (x, y) {
  this.p = new S.Point({
    x,
    y,
    z: 5,
    a: 1,
    h: 0,
  });

  this.e = 0.07;
  this.s = true;

  this.c = new S.Color(255, 255, 255, this.p.a);

  this.t = this.clone();
  this.q = [];
};

S.Dot.prototype = {
  clone() {
    return new S.Point({
      x: this.x,
      y: this.y,
      z: this.z,
      a: this.a,
      h: this.h,
    });
  },

  draw() {
    this.c.a = this.p.a;
    S.Drawing.drawCircle(this.p, this.c);
  },

  moveTowards(n) {
    const details = this.distanceTo(n, true);
    const dx = details[0];
    const dy = details[1];
    const d = details[2];
    const e = this.e * d;

    if (this.p.h === -1) {
      this.p.x = n.x;
      this.p.y = n.y;
      return true;
    }

    if (d > 1) {
      this.p.x -= (dx / d) * e;
      this.p.y -= (dy / d) * e;
    } else if (this.p.h > 0) {
      this.p.h--;
    } else {
      return true;
    }

    return false;
  },

  update() {
    if (this.moveTowards(this.t)) {
      const p = this.q.shift();

      if (p) {
        this.t.x = p.x || this.p.x;
        this.t.y = p.y || this.p.y;
        this.t.z = p.z || this.p.z;
        this.t.a = p.a || this.p.a;
        this.p.h = p.h || 0;
      } else if (this.s) {
        this.p.x -= Math.sin(Math.random() * 3.142);
        this.p.y -= Math.sin(Math.random() * 3.142);
      } else {
        this.move(
          new S.Point({
            x: this.p.x + Math.random() * 50 - 25,
            y: this.p.y + Math.random() * 50 - 25,
          })
        );
      }
    }

    let d = this.p.a - this.t.a;
    this.p.a = Math.max(0.1, this.p.a - d * 0.05);
    d = this.p.z - this.t.z;
    this.p.z = Math.max(1, this.p.z - d * 0.05);
  },

  distanceTo(n, details) {
    const dx = this.p.x - n.x;
    const dy = this.p.y - n.y;
    const d = Math.sqrt(dx * dx + dy * dy);

    return details ? [dx, dy, d] : d;
  },

  move(p, avoidStatic) {
    if (!avoidStatic || (avoidStatic && this.distanceTo(p) > 1)) {
      this.q.push(p);
    }
  },

  render() {
    this.update();
    this.draw();
  },
};

S.ShapeBuilder = (function () {
  const gap = 11;
  const shapeCanvas = document.createElement("canvas");
  const shapeContext = shapeCanvas.getContext("2d");
  const fontSize = 500;
  const fontFamily = "Avenir, Helvetica Neue, Helvetica, Arial, sans-serif";

  function fit() {
    shapeCanvas.width = Math.floor(window.innerWidth / gap) * gap;
    shapeCanvas.height = Math.floor(window.innerHeight / gap) * gap;
    shapeContext.fillStyle = "red";
    shapeContext.textBaseline = "middle";
    shapeContext.textAlign = "center";
  }

  function processCanvas() {
    const pixels = shapeContext.getImageData(
      0,
      0,
      shapeCanvas.width,
      shapeCanvas.height
    ).data;
    const dots = [];
    let x = 0;
    let y = 0;
    let fx = shapeCanvas.width;
    let fy = shapeCanvas.height;
    let w = 0;
    let h = 0;

    for (let p = 0; p < pixels.length; p += 4 * gap) {
      if (pixels[p + 3] > 0) {
        dots.push(
          new S.Point({
            x,
            y,
          })
        );

        w = x > w ? x : w;
        h = y > h ? y : h;
        fx = x < fx ? x : fx;
        fy = y < fy ? y : fy;
      }

      x += gap;

      if (x >= shapeCanvas.width) {
        x = 0;
        y += gap;
        p += gap * 4 * shapeCanvas.width;
      }
    }

    return { dots, w: w + fx, h: h + fy };
  }

  function setFontSize(s) {
    shapeContext.font = "bold " + s + "px " + fontFamily;
  }

  function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
  }

  function init() {
    fit();
    window.addEventListener("resize", fit);
  }

  // Init
  init();

  return {
    imageFile(url, callback) {
      const image = new Image();
      const a = S.Drawing.getArea();

      image.onload = function () {
        shapeContext.clearRect(0, 0, shapeCanvas.width, shapeCanvas.height);
        shapeContext.drawImage(this, 0, 0, a.h * 0.6, a.h * 0.6);
        callback(processCanvas());
      };

      image.onerror = function () {
        callback(S.ShapeBuilder.letter("What?"));
      };

      image.src = url;
    },

    circle(d) {
      const r = Math.max(0, d) / 2;
      shapeContext.clearRect(0, 0, shapeCanvas.width, shapeCanvas.height);
      shapeContext.beginPath();
      shapeContext.arc(r * gap, r * gap, r * gap, 0, 2 * Math.PI, false);
      shapeContext.fill();
      shapeContext.closePath();

      return processCanvas();
    },

    letter(l) {
      let s = 0;

      setFontSize(fontSize);
      s = Math.min(
        fontSize,
        (shapeCanvas.width / shapeContext.measureText(l).width) *
          0.8 *
          fontSize,
        (shapeCanvas.height / fontSize) * (isNumber(l) ? 1 : 0.45) * fontSize
      );
      setFontSize(s);

      shapeContext.clearRect(0, 0, shapeCanvas.width, shapeCanvas.height);
      shapeContext.fillText(l, shapeCanvas.width / 2, shapeCanvas.height / 2);

      return processCanvas();
    },

    rectangle(w, h) {
      const dots = [];
      const width = gap * w;
      const height = gap * h;

      for (let y = 0; y < height; y += gap) {
        for (let x = 0; x < width; x += gap) {
          dots.push(
            new S.Point({
              x,
              y,
            })
          );
        }
      }

      return { dots, w: width, h: height };
    },
  };
})();

S.Shape = (function () {
  const dots = [];
  let width = 0;
  let height = 0;
  let cx = 0;
  let cy = 0;

  function compensate() {
    const a = S.Drawing.getArea();
    // 文本位置
    cx = a.w / 2 - width / 2;
    cy = a.h / 2 - height / 2 - height / 3 - 20;
  }

  return {
    shuffleIdle() {
      const a = S.Drawing.getArea();

      for (let d = 0; d < dots.length; d++) {
        if (!dots[d].s) {
          dots[d].move({
            x: Math.random() * a.w,
            y: Math.random() * a.h,
          });
        }
      }
    },

    switchShape(n, fast) {
      let size;
      const a = S.Drawing.getArea();

      width = n.w;
      height = n.h;

      compensate();

      if (n.dots.length > dots.length) {
        size = n.dots.length - dots.length;
        for (let d = 1; d <= size; d++) {
          dots.push(new S.Dot(a.w / 2, a.h / 2));
        }
      }

      let d = 0;
      let i = 0;

      while (n.dots.length > 0) {
        i = Math.floor(Math.random() * n.dots.length);
        dots[d].e = fast ? 0.25 : dots[d].s ? 0.14 : 0.11;

        if (dots[d].s) {
          dots[d].move(
            new S.Point({
              z: Math.random() * 20 + 10,
              a: Math.random(),
              h: 18,
            })
          );
        } else {
          dots[d].move(
            new S.Point({
              z: Math.random() * 5 + 5,
              h: fast ? 18 : 30,
            })
          );
        }

        dots[d].s = true;
        dots[d].move(
          new S.Point({
            x: n.dots[i].x + cx,
            y: n.dots[i].y + cy,
            a: 1,
            z: 5,
            h: 0,
          })
        );

        n.dots = n.dots.slice(0, i).concat(n.dots.slice(i + 1));
        d++;
      }

      for (let i = d; i < dots.length; i++) {
        if (dots[i].s) {
          dots[i].move(
            new S.Point({
              z: Math.random() * 20 + 10,
              a: Math.random(),
              h: 20,
            })
          );

          dots[i].s = false;
          dots[i].e = 0.04;
          dots[i].move(
            new S.Point({
              x: Math.random() * a.w,
              y: Math.random() * a.h,
              a: 0.3, // .4
              z: Math.random() * 4,
              h: 0,
            })
          );
        }
      }
    },

    render() {
      for (let d = 0; d < dots.length; d++) {
        dots[d].render();
      }
    },
  };
})();

export default S;
