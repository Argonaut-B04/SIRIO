import React, { Component } from 'react';
import classes from './SirioComponentHeader.module.css';

class SirioComponentHeader extends Component {
    render() {
        const { headerWrapper } = classes;
        const { title, headerButton, betweenTitleSubtitle, subtitle } = this.props;
        return (
            <div className={headerWrapper}>
                {title ?
                    <div className="row">
                        <h2 className={classes.title}>
                            {title}
                        </h2>
                        {headerButton ?
                            <div className="ml-auto">{headerButton}</div>
                            :
                            ""
                        }
                    </div>
                    :
                    ""
                }
                {betweenTitleSubtitle ?
                    <div className="row">
                        {betweenTitleSubtitle}
                    </div>
                    :
                    ""
                }

                {subtitle ?
                    <div className="row">
                        <h5 className={classes.subtitle}>
                            {subtitle}
                        </h5>
                    </div>
                    :
                    ""
                }
            </div>
        );
    }
}

export default SirioComponentHeader;
