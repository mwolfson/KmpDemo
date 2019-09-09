//
//  ViewController.swift
//  KotlinIOS
//
//  Created by Mike Wolfson on 8/26/19.
//  Copyright © 2019 Mike Wolfson. All rights reserved.
//

import UIKit
import SharedCode

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        CommonKt.pubLog(message: "iOS log Message", level: "WARN", tag: "MSWAPPLE")
        
        let messageLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
        messageLabel.center = CGPoint(x: 160, y: 285)
        messageLabel.textAlignment = .left
        messageLabel.font = messageLabel.font.withSize(14)
        messageLabel.text = CommonKt.createApplicationScreenMessage()
        view.addSubview(messageLabel)
        
        let timestampLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
        timestampLabel.center = CGPoint(x: 160, y: 305)
        timestampLabel.textAlignment = .left
        timestampLabel.font = timestampLabel.font.withSize(14)
        timestampLabel.text = CommonKt.createTimestampScreenMessage()
        view.addSubview(timestampLabel)
    }
}

